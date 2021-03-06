package com.github.aelmod.ssn2.microblog.commentary;

import com.github.aelmod.ssn2.microblog.Microblog;
import com.github.aelmod.ssn2.user.User;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Setter
public class CommentaryForm {

    @NotEmpty
    private String text;

    private Integer microblogId;

    private User user;

    public Commentary toCommentary() {
        Commentary commentary = new Commentary();
        Microblog microblog = new Microblog();
        microblog.setId(microblogId);
        commentary.setUser(user);
        commentary.setCreationTime(new Date());
        commentary.setText(text);
        commentary.setMicroblog(microblog);
        return commentary;
    }
}
