package by.jrr.moodle.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    private String title;
    private String subtitle;
    @Lob
    private String text;

}
