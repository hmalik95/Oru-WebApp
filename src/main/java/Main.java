import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

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
            int hashed = inputPassword.hashCode();
            boolean userExist = false;
            db.getUsernameAndPassword(inputUsername,hashed);
            System.out.println(inputUsername + "\t" + db.getDbUsername() + "\t" + inputPassword);
            if (db.getDbUsername().equals(inputUsername) && db.getDbPassword() == hashed)
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

        get("/login", (req, res) ->{
            Map<String, String> model = new HashMap<>();
            model.put("username", req.queryParams("username"));
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());
        /* Sending the user to the register page */
        get("/register", (req, res) -> {
            return new ModelAndView(null, "register.hbs");
        }, new HandlebarsTemplateEngine());


        post("/register", (req, res) -> {
            String inputU = req.queryParams("username");
            String inputP = req.queryParams("password");
            int hashed = inputP.hashCode();
            Boolean userExist = false;
            if (db.getUsername(inputU) != false)
            {
                userExist = true;
            }
            if (!userExist)
            {
                db.insert(inputU,hashed);
                res.redirect("/");
                return null;
            }
            else {
                res.redirect("/register");
                return null;
            }


        }, new HandlebarsTemplateEngine());

        /*
        post("/post",(req,res)->{
            String inputCategory=req.queryParams("category");
            String inputText=req.queryParams("text");
            db.insertPost(inputCategory,inputText);
            List<List<String>> test = db.getAllPosts(inputCategory);
            System.out.println("Testet startar nu:");
            System.out.println(test.get(0).get(1));

            int arrayLength = test.get(0).size();
            res.redirect("/login");
            for (int i= 0; i< arrayLength; i++)
            {
                System.out.println(test.get(0).get(i));
                System.out.println(test.get(1).get(i));

                String word = test.get(1).get(i);
                //res.body(word);
            }
            return null;
        });
        */
        post("/posts", (req, res) -> {
            String text = req.queryParams("text");
            String category = req.queryParams("category");
            db.insertPost(category, text);

            res.redirect("/posts");
            return null;
        });
        get("/posts", (req, res) -> {
                Map<String, Object> model = new HashMap<>();

                model.put("fullPostsTextu", db.test());
                return new ModelAndView(model, "post.hbs");
        }, new HandlebarsTemplateEngine());


    }

}

