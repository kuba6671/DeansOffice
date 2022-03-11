package OfficeDeans.packagePerson;

public abstract class Person {
    private String name;
    private String surname;
    private int age;
    private String phoneNumber;
    private String mail;
    private String password;

    public Person(String name, String surname, int age, String phoneNumber, String mail, String password) {
        setName(name);
        setSurname(surname);
        setAge(age);
        setPhoneNumber(phoneNumber);
        setMail(mail);
        setPassword(password);
    }

    public Person(String name, String surname, int age, String phoneNumber, String mail) {
        setName(name);
        setSurname(surname);
        setAge(age);
        setPhoneNumber(phoneNumber);
        setMail(mail);
    }



    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setName(String name) {
        if(name == null) {
            throw new RuntimeException("Name cannot be null");
        }
        this.name = name;
    }

    public void setSurname(String surname) {
        if(surname == null)
            throw new RuntimeException("Surname cannot be null");
        this.surname = surname;
    }

    public void setAge(int age) {
        if(age <=0 || age >100)
            throw new RuntimeException("Age must be a number between 0-100");
        this.age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() != 9){
            throw new RuntimeException("Phone Number Should be 9 Digits Long");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        if(password.length() < 4){
            throw new RuntimeException("Password cannot be shorter than 4 characters");
        }
        this.password = password;
    }

}
