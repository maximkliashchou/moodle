package by.jrr.project.bean;

import by.jrr.auth.bean.User;
import by.jrr.constant.Endpoint;
import by.jrr.feedback.bean.Reviewable;
import by.jrr.feedback.bean.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue implements Reviewable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long Id;
    private Long issueId; //it has two ids: on for issue, one for row - to create a history
    @Transient
    private Project project = new Project(); // TODO: 11/05/20 handle npe otherwise
    private Long projectId;
    @Enumerated(value = EnumType.STRING)
    private IssueType issueType;
    @Enumerated(value = EnumType.STRING)
    private IssueStatus issueStatus;
    private String name;
    private String summary;
    @Lob
    private String description;
    @Lob
    private String reproSteps;
    @Transient
    private Issue parentIssue;
    private Long parentId;
    @Transient
    private User assignee = new User(); // TODO: 11/05/20 handle npe otherwise
    private Long assigneeUserId;
    private LocalDateTime timeStamp;
    @Transient
    private User submitter = new User(); // TODO: 11/05/20 handle npe otherwise
    private Long submitterUserId;
    private boolean lastInHistory;
    @Transient
    private List<Issue> history = new ArrayList<>();
    public String getLink() { // TODO: 11/05/20 model should be divided from view
        return Endpoint.PROJECT+"/"+projectId+Endpoint.ISSUE+"/"+this.getIssueId();
    } // TODO: 27/05/20 link should be independent of bean
    public String getProjectLink() { // TODO: 11/05/20 model should be divided from view
        return Endpoint.PROJECT+"/"+projectId;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "Id=" + Id +
                ", issueId=" + issueId +
                ", projectId=" + projectId +
                ", issueType=" + issueType +
                ", issueStatus=" + issueStatus +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", reproSteps='" + reproSteps + '\'' +
                ", parentIssue=" + parentIssue +
                ", parentId=" + parentId +
                ", assignee=" + assignee +
                ", assigneeUserId=" + assigneeUserId +
                ", timeStamp=" + timeStamp +
                ", submitter=" + submitter +
                ", submitterUserId=" + submitterUserId +
                ", lastInHistory=" + lastInHistory +
                '}';
    }

    @Override
    public EntityType getType() {
        return EntityType.ISSUE;
    }
}
