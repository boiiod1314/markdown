package com.doc.com.doc.controller;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by huwei on 2017/9/11.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "forward:/list.html";
    }

    @GetMapping("/index.html")
    public String index(ModelMap map,String f) {
        map.put("content",this.getContent(f));
        return "index";
    }

    @GetMapping("/list.html")
    public String list(ModelMap map) throws UnsupportedEncodingException {
        map.put("mdlist",this.getMdlist());
        return "list";
    }

    @GetMapping("/edit.html")
    public String edit(ModelMap map,String f){
        map.put("content",this.getContent(f));
        if(f==null) f = "xxx.md";
        map.put("filename",f);
        return "edit";
    }

    @GetMapping("/editlist.html")
    public String editlist(ModelMap map){
        map.put("mdlist",this.getMdlist());
        return "editlist";
    }

    @GetMapping("/del.html")
    public String del(String f){
        if(f==null || (f = f.trim()).length()==0) return "redirect:/editlist.html";

        String path = this.getClass().getClassLoader().getResource("markdown").getPath();
        File dir = new File(path);
        File file = new File(dir,f);
        if(!file.exists()){
            return "redirect:/editlist.html";
        }

        String delpath = this.getClass().getClassLoader().getResource("markdown_del").getPath();
        File deldir = new File(delpath);
        if(!deldir.exists()){
            deldir.mkdirs();
        }

        File delfile = new File(deldir,f);
        int i = 1;
        do{
            if(!delfile.exists()){
                break;
            }
            delfile = new File(deldir,f+"_"+i);
            i++;
        }while (i<Integer.MAX_VALUE );

        file.renameTo(delfile);

        return "redirect:/editlist.html";
    }

    @ResponseBody
    @PostMapping("/save.html")
    public String editlist(String f,String content) throws IOException {
        if(f==null || (f=f.trim()).length()==0 || content==null) return "fail";
        String path = this.getClass().getClassLoader().getResource("markdown").getPath();
        File dir = new File(path);
        File file = new File(dir,f);
        FileUtils.writeStringToFile(file,content);
        return "success";
    }


    public String getContent(String f){
        try {
            URL url = this.getClass().getClassLoader().getResource("markdown/" + f);
            String content = FileUtils.readFileToString(FileUtils.toFile(url));
            return content;
        }catch (Exception e){
            return "";
        }
    }

    public List<String> getMdlist() {
        String path = this.getClass().getClassLoader().getResource("markdown").getPath();
        File dir = new File(path);
        File[] files = dir.listFiles();
        List<String> mdlist = new ArrayList<>();
        URLCodec urlCodec = new URLCodec();
        for(File md:files){
            //mdlist.add(urlCodec.encode(md.getName(), CharEncoding.UTF_8));
            mdlist.add(md.getName());
        }
        return mdlist;
    }


}
