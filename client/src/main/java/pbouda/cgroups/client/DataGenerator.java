package pbouda.cgroups.client;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DataGenerator {

    private static final Lorem LOREM = LoremIpsum.getInstance();
    private static final Path OUTPUT = Path.of(System.getProperty("java.io.tmpdir"), "init.sql");

    private static final String STRUCTURE = """
            CREATE TABLE person (
                id int,
                firstname varchar(255),
                lastname varchar(255),
                city varchar(255),
                country varchar(255),
                phone varchar(255),
                political_opinion text
            );
            """;

    public static void main(String[] args) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(STRUCTURE + "\n");

        for (int i = 1; i <= 1000; i++) {
            String insert = """
                    INSERT INTO person (id, firstname, lastname, city, country, phone, political_opinion) 
                    VALUES (%s, '%s', '%s', '%s', '%s', '%s', '%s');
                    """.formatted(
                    i,
                    LOREM.getFirstName(),
                    convert(LOREM.getLastName()),
                    convert(LOREM.getCity()),
                    convert(LOREM.getCountry()),
                    LOREM.getPhone(),
                    LOREM.getParagraphs(2, 10));

            builder.append(insert);
        }

        Files.writeString(OUTPUT, builder.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String convert(String value) {
        return value.replace("'", "");
    }
}
