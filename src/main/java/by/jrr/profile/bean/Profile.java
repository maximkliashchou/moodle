package by.jrr.profile.bean;

import by.jrr.auth.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @javax.persistence.Id
    @GeneratedValue
    private Long Id;
    @Transient
    private User user;
    private long userId;

    @Lob
    private byte[] avatar; // TODO: 01/06/20  consider to save files in separate table, and store here only ids to them

}
