package com.File_withe_Database2.FileupladDB2;


import org.springframework.web.bind.annotation.GetMapping;

public class test {

    @GetMapping("/test")
    public  String test()
    {
           return  "ok";
    }


}
