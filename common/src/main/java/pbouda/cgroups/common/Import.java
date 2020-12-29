package pbouda.cgroups.common;

import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Import {

    public static void main(String[] args) throws Exception {
        String initScript = "init.sql";
        URL resource = ScriptUtils.class.getClassLoader().getResource(initScript);
        String scripts = IOUtils.toString(resource, StandardCharsets.UTF_8);

        ArrayList<String> statements = new ArrayList<>();
        ScriptUtils.splitSqlScript(initScript, scripts, ";", "--", "/*", "*/", statements);

        Driver jdbcDriver = (Driver) Class.forName("org.postgresql.Driver").newInstance();
        String url = "jdbc:postgresql://localhost:26257/postgres";

        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "");

        Connection connection = jdbcDriver.connect(url, info);
        Statement statement = connection.createStatement();
        for (String line : statements) {
            statement.execute(line);
        }
    }
}
