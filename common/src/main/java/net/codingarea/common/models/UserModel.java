package net.codingarea.common.models;

import lombok.*;
import net.codingarea.common.domain.Model;
import xyz.morphia.annotations.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@Entity(value = "users", noClassnameStored = true)
@Indexes({
        @Index(options = @IndexOptions(unique = true), fields = {
                @Field("uuid")
        }),
        @Index(options = @IndexOptions(unique = true), fields = {
                @Field("latestName")
        })
})
public class UserModel extends Model {

    private UUID uuid;

    private String latestName;

    @PrePersist
    public void prePersist() {
        super.prePersist();
    }
}
