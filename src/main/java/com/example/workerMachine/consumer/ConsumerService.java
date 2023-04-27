package com.example.workerMachine.consumer;

import com.example.DataForMachine;
import com.example.Result;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RestController
public class ConsumerService {
    Result result=new Result();
    private ServerProperties serverProperties;
    //MultipartFile multipartFile,multipartFile_2;

    RestTemplate restTemplate= new RestTemplate();
    String uri="http://localhost:8081";
    int port;
    boolean flag=true;

    public ConsumerService(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        port=serverProperties.getPort();
        doRegistration();
    }

    public void doRegistration(){
        try {
            restTemplate.postForEntity(uri+"/registration",port,String.class);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @GetMapping("/alive")
    public String alive(){
        return "alive";
    }

    /*Thread run = new Thread(new Runnable() {
        @Override
        public void run() {
            while(flag){
                File jarFile = new File(multipartFile.getOriginalFilename());
                doFile(jarFile,multipartFile);

                File dataObjectFile = new File(multipartFile_2.getOriginalFilename());
                doFile(dataObjectFile,multipartFile_2);

                DataForMachine dataForMachine=new DataForMachine();
                try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataObjectFile)))
                {
                    dataForMachine=(DataForMachine) ois.readObject();
                }
                catch(Exception ex){System.out.println(ex.getMessage());}

                String graph=new String(Arrays.deepToString(dataForMachine.getGraph()));
                graph=graph.replace(" ","");
                String list= String.valueOf(dataForMachine.getArrayLists());
                list=list.replace(" ","");
                String taxi= String.valueOf(dataForMachine.getTaxi());
                taxi=taxi.replace(" ","");
                String passengers= String.valueOf(dataForMachine.getNumberOfPassengers());
                String listQueue=String.valueOf(dataForMachine.getArrayListQueue());

                System.out.println(listQueue);

                String generalString=" "+graph+" "+list+" "+taxi+" "+passengers+" "+listQueue;
                Process cat= null;
                try {
                    cat = Runtime.getRuntime().exec("java -jar " +jarFile.getName()+generalString);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(cat.getInputStream()));
                try {
                    result.setSum(reader.readLine());
                    result.setQueue(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flag=false;
                System.out.println(result);
            }
        }
    });*/

    @PostMapping(value = "/getData", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void get(@RequestParam("jar") MultipartFile multipartFile,
                    @RequestParam("objectForMachine") MultipartFile multipartFile_2) throws IOException {
        //flag=true;
        File dataObjectFile = new File(multipartFile_2.getOriginalFilename());
        doFile(dataObjectFile,multipartFile_2);
        File jarFile = new File(multipartFile.getOriginalFilename());
        doFile(jarFile,multipartFile);
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                /*System.out.println(multipartFile.getSize()+"multif");
                File jarFile = new File(UUID.randomUUID()+".jar");
                    doFile(jarFile,multipartFile);
                try(OutputStream os = new FileOutputStream(jarFile)) {
                    os.write(multipartFile.getBytes());
                    os.close();
                    os.flush();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                    /*File dataObjectFile = new File(multipartFile_2.getOriginalFilename());
                    doFile(dataObjectFile,multipartFile_2);*/

                    DataForMachine dataForMachine=new DataForMachine();

                    try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataObjectFile)))
                    {
                        dataForMachine=(DataForMachine) ois.readObject();
                    }
                    catch(Exception ex){System.out.println(ex.getMessage());}

                    String graph=new String(Arrays.deepToString(dataForMachine.getGraph()));
                    graph=graph.replace(" ","");
                    String list= String.valueOf(dataForMachine.getArrayLists());
                    list=list.replace(" ","");
                    String taxi= String.valueOf(dataForMachine.getTaxi());
                    taxi=taxi.replace(" ","");
                    String passengers= String.valueOf(dataForMachine.getArrayLists().size());
                    String listQueue=String.valueOf(dataForMachine.getArrayListQueue());
                    listQueue=listQueue.replace(" ","");

                    String generalString=" "+graph+" "+list+" "+taxi+" "+passengers+" "+listQueue;
                    //System.out.println(generalString);
                    Process cat= null;
                    try {
                        cat = Runtime.getRuntime().exec("java -jar " +jarFile.getName()+generalString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    jarFile.deleteOnExit();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(cat.getInputStream()));
                    try {
                        result.setSum(reader.readLine());
                        result.setQueue(reader.readLine());
                        result.setPort(String.valueOf(port));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //flag=false;
                    restTemplate.postForEntity(uri+"/getResult",result,Result.class);
                    //System.out.println(result);


            }
        });
        run.start();

       // return result.getSum();
        /*File jarFile = new File(multipartFile.getOriginalFilename());
        doFile(jarFile,multipartFile);

        File dataObjectFile = new File(multipartFile_2.getOriginalFilename());
        doFile(dataObjectFile,multipartFile_2);

        DataForMachine dataForMachine=new DataForMachine();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataObjectFile)))
        {
            dataForMachine=(DataForMachine) ois.readObject();
        }
        catch(Exception ex){System.out.println(ex.getMessage());}

        String graph=new String(Arrays.deepToString(dataForMachine.getGraph()));
        graph=graph.replace(" ","");
        String list= String.valueOf(dataForMachine.getArrayLists());
        list=list.replace(" ","");
        String taxi= String.valueOf(dataForMachine.getTaxi());
        taxi=taxi.replace(" ","");
        String passengers= String.valueOf(dataForMachine.getNumberOfPassengers());
        String generalString=" "+graph+" "+list+" "+taxi+" "+passengers+" "+start+" "+end;
        Process cat=Runtime.getRuntime().exec("java -jar " +jarFile.getName()+generalString);

        BufferedReader reader = new BufferedReader(new InputStreamReader(cat.getInputStream()));
        Result result=new Result();
        result.setSum(reader.readLine());
        result.setQueue(reader.readLine());

        System.out.println(result);*/

        /*ResponseEntity<String> response
                = restTemplate.getForEntity(uri, String.class);
        return response.getBody();*/
    }

    public  void doFile(File file,MultipartFile multipartFile){
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
            os.close();
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
