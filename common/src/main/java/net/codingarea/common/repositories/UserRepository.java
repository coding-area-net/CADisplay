package net.codingarea.common.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.codingarea.common.Constants;
import net.codingarea.common.domain.Repository;
import net.codingarea.common.models.UserModel;
import xyz.morphia.Datastore;

import java.util.UUID;

@Singleton
public class UserRepository extends Repository<UserModel> {

    @Inject
    protected UserRepository(@Named(Constants.DATASTORE) Datastore datastore) {
        super(UserModel.class, datastore);
    }

    public UserModel findByUuid(UUID uuid) {
        return this.createQuery()
                .field("uuid").equal(uuid)
                .get();
    }

    public UserModel findByLatestName(String latestName) {
        return this.createQuery()
                .field("latestName").equalIgnoreCase(latestName)
                .get();
    }
}
