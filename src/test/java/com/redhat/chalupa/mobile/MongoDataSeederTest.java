package com.redhat.chalupa.mobile;

import com.redhat.chalupa.mobile.dal.UserDao;
import com.redhat.chalupa.mobile.domain.model.User;
import com.redhat.chalupa.mobile.domain.model.User.Location;
import com.redhat.chalupa.mobile.domain.model.User.Name;
import com.redhat.chalupa.mobile.domain.model.User.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.redhat.chalupa.mobile.domain.model.User.Gender.FEMALE;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MongoDataSeederTest {

    private static final String SEED = "/seed-test.json";
    private static final User USER = User.builder()
            .gender(FEMALE)
            .name(new Name("miss", "alison", "reid"))
            .location(new Location("1097 the avenue", "Newbridge", "ohio", 28782))
            .email("alison.reid@example.com")
            .username("tinywolf709")
            .password("rockon")
            .salt("lypI10wj")
            .md5("bbdd6140e188e3bf68ae7ae67345df65")
            .sha1("4572d25c99aa65bbf0368168f65d9770b7cacfe6")
            .sha256("ec0705aec7393e2269d4593f248e649400d4879b2209f11bb2e012628115a4eb")
            .registered(1237176893L)
            .dob(932871968L)
            .phone("031-541-9181")
            .cell("081-647-4650")
            .PPS("3302243T")
            .picture(new Picture(
                    "https://randomuser.me/api/portraits/women/60.jpg",
                    "https://randomuser.me/api/portraits/med/women/60.jpg",
                    "https://randomuser.me/api/portraits/thumb/women/60.jpg"
            ))
            .build();

    @Mock
    private UserDao userDao;

    @Test
    public void userShouldBeParsedAndImported() {
        final MongoDataSeeder seeder = new MongoDataSeeder(userDao);
        seeder.seed(this.getClass().getResourceAsStream(SEED));
        verify(userDao).save(USER);
    }
}
