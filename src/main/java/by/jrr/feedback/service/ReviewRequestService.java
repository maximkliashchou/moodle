package by.jrr.feedback.service;

import by.jrr.feedback.bean.*;
import by.jrr.feedback.repository.ReviewRequestRepository;
import by.jrr.profile.bean.Profile;
import by.jrr.profile.service.ProfilePossessesService;
import by.jrr.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewRequestService {

    @Autowired
    ReviewRequestRepository reviewRequestRepository;
    @Autowired
    ProfileService profileService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ItemService itemService;
    @Autowired
    ProfilePossessesService pss;

    public ReviewRequest createNewReviewRequest(Item item, Reviewable reviewable) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setItemId(item.getId());
        reviewRequest.setReviewedEntityId(reviewable.getId());
        reviewRequest.setRequesterProfileId(profileService.getCurrentUserProfile().getId());
        reviewRequest.setCreatedDate(LocalDateTime.now());
        reviewRequest.setReviewResultOnClosing(ReviewResult.NONE);
        return reviewRequestRepository.save(reviewRequest);
    }

    public ReviewRequest updateMessageAndLinkOnReviewRequest(ReviewRequest reviewRequest) {
        // only reviewer notes and link to request could be updated,
        // because other fields from save form data endpoint comes empty (todo is it true and whY? Is it fixable and should it be? Otherwise could be secure? could user modify fields in POST and save new IDs?
        Optional<ReviewRequest> savedReviewRequest = reviewRequestRepository.findById(reviewRequest.getId());
        if (savedReviewRequest.isPresent()) {
            ReviewRequest updatedRequest = savedReviewRequest.get();
            updatedRequest.setRequesterNotes(reviewRequest.getRequesterNotes());
            updatedRequest.setLink(reviewRequest.getLink());
            return reviewRequestRepository.save(updatedRequest);

        }
        return null; // TODO: 28/05/20 return Optional and handle it with logger in controller
    }

    public ReviewRequest closeReviewRequest(ReviewRequest reviewRequest) {
        // only status on close and closed date could be updated
        // because other fields from save form data endpoint comes empty
        Optional<ReviewRequest> savedReviewRequest = reviewRequestRepository.findById(reviewRequest.getId());
        if (savedReviewRequest.isPresent()) {
            ReviewRequest updatedRequest = savedReviewRequest.get();
            updatedRequest.setReviewResultOnClosing(reviewRequest.getReviewResultOnClosing());
            updatedRequest.setClosedDate(LocalDateTime.now());
            return reviewRequestRepository.save(updatedRequest);

        }
        return null; // TODO: 28/05/20 return Optional and handle it with logger in controller
    }

    public List<ReviewRequest> findReviewRequestForUser(Long profileId) {
        List<ReviewRequest> reviewRequestList = reviewRequestRepository.findAllByRequesterProfileId(profileId);
        reviewRequestList.stream()
                .map(this::setRequesterProfileToReviewRequest)
                .map(this::setReviewsToReviewRequest)
                .map(this::setItemsToReviewRequest)
                .map(this::setReviewedEntityToItemInReviewRequest)
                .collect(Collectors.toList());
        return reviewRequestList;
    }

    private ReviewRequest setReviewsToReviewRequest(ReviewRequest rr) {
        rr.setReviews(reviewService.findAllByReviewRequestId(rr.getId()));
        return rr;
    }
    private ReviewRequest setItemsToReviewRequest(ReviewRequest rr) {
        rr.setItem(itemService.getItemByReviewRequest(rr));
        return rr;
    }
    private ReviewRequest setRequesterProfileToReviewRequest(ReviewRequest rr) {
        rr.setRequesterProfile(profileService.findProfileByProfileId(rr.getRequesterProfileId()).orElseGet(Profile::new));
        return rr;
    }
    private ReviewRequest setReviewedEntityToItemInReviewRequest(ReviewRequest rr) {
        rr.getItem()
            .setReviewedEntity(itemService.getReviewableByReviewableId(rr.getReviewedEntityId()));
        return rr;
    }
}
