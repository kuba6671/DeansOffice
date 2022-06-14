import OfficeDeans.packagePerson.Student;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTest {
    Student test;

    StudentTest(){
        test = new Student(123,"pass","testName",
                "testSurname",20,"123456789","asd@gmail.com",
                9999, "testFOS");
    }
    @Test
    public void studentTesting(){
        assertAll("address name",
                () -> assertEquals("testName", test.getName()),
                () -> assertEquals("testSurname", test.getSurname())
        );
    }
    @Test
    @DisplayName("Name cannot be null")
    public void shouldThrownRuntimeExceptionWhenNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            test.setName(null);
        });

    }
    @Test
    @DisplayName("Surname cannot be null")
    public void shouldThrownRuntimeExceptionWhenSurnameIsNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            test.setSurname(null);
        });

    }

    @Test
    @DisplayName("Age must be a number between 0 and 100")
    public void shouldThrownRuntimeExceptionWhenAgeIsOutOfRange(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            test.setAge(-10);
        });
    }

    @Test
    @DisplayName("Phone Number Should be 9 digits long")
    public void shouldThrownRuntimeExceptionWhenPhoneNumberIsInvalid(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            test.setPhoneNumber("123");
        });
    }

    @Test
    @DisplayName("Password cannot be shorter than four characters")
    public void shouldThrownRuntimeExceptionWhenPasswordIsTooShort(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            test.setPassword("a");
        });
    }

}

