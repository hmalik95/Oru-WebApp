import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Main extends dbConnection {
    private static test_db_insert_and_connect db = new test_db_insert_and_connect();



    public static void main(String[] args) {
        staticFileLocation("/public");
        get("/",(req, res) -> {
            return new ModelAndView(null, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/login", (req, res) -> {
            String inputUsername = req.queryParams("username");
            String inputPassword = req.queryParams("password");
            boolean userExist = false;
            db.getUsernameAndPassword(inputUsername,inputPassword);
            System.out.println(inputUsername + "\t" + db.getDbUsername() + "\t" + inputPassword);
            if (db.getDbUsername().equals(inputUsername) && db.getDbPassword().equals(inputPassword))
            {
                userExist = true;
            }
            if (!userExist) {
                res.redirect("/");
                return null;
            }
            else
            {
                Map<String, String> model = new HashMap<>();
                model.put("username", req.queryParams("username"));

                return new ModelAndView(model, "login.hbs");
            }


        }, new HandlebarsTemplateEngine());

        /* Sending the user to the register page */
        get("/register", (req, res) -> {
            return new ModelAndView(null, "register.hbs");
        }, new HandlebarsTemplateEngine());


        post("/register", (req, res) -> {
            String inputU = req.queryParams("username");
            String inputP = req.queryParams("password");
            Boolean userExist = false;
            if (db.getUsername(inputU) != false)
            {
                userExist = true;
            }
            if (!userExist)
            {
                db.insert(inputU,inputP);
                res.redirect("/");
                return null;
            }
            else {
                res.redirect("/register");
                return null;
            }


        }, new HandlebarsTemplateEngine());

        post("/post",(req,res)->{
            String inputCategory=req.queryParams("category");
            String inputText=req.queryParams("text");
            db.insertPost(inputCategory,inputText);
            res.redirect("/");
            return null;
        });


    }

}

