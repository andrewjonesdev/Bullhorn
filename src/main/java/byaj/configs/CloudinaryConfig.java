package byaj.configs;

/**
 * Created by student on 7/11/17.
 */
import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class CloudinaryConfig {
    private Cloudinary cloudinary;
    @Value("${cloudinary.cloudname}") private String cloud;

    @Autowired
    public CloudinaryConfig(@Value("${cloudinary.apikey}") String key,
                            @Value("${cloudinary.apisecret}") String secret,
                            @Value("${cloudinary.cloudname}") String cloud){
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName=cloud;
        cloudinary.config.apiSecret=secret;
        cloudinary.config.apiKey=key;
    }
    public Map upload(Object file, Map options){
        try{
            return cloudinary.uploader().upload(file, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String createUrl(String name, int width, int height, String action, String effect){
        return cloudinary.url()
                .transformation(new Transformation().width(width).height(height).border("2px_solid_black").crop(action).effect(effect))
                .imageTag(name);
    }
    public String createUrlNoFormat(String name, int width, int height, String action, String effect, int border){
        //
        //Take String name and break by "/" and on the 7th you'll have the name Or use array.length-1
        //http://res.cloudinary.com/andrewjonesdev/image/upload/v1499808350/q0jfkcq9gvukrqvsx3gl.jpg
        String[] url = name.split("/");
        return String.format("http://res.cloudinary.com/%s/image/upload/bo_%spx_solid_black,c_%s,e_%s,h_%s,w_%s/%s", cloud, border, action, effect,height, width, url[url.length-1]);
    }
    public String createUrlSuper(String name, int width, int height, String action, String effect, int border){
        String[] url = name.split("/");
        String[] superUrl =  (cloudinary.url()
                .transformation(new Transformation().width(width).height(height).border(border+"px_solid_black").crop(action).effect(effect))
                .imageTag(url[url.length-1])).split("'");

        return superUrl[1];
    } 

    public String createUrlSuperPost(String name, int height, String action, int border){
        String[] url = name.split("/");
        String[] superUrl =  (cloudinary.url()
                .transformation(new Transformation().height(height).border(border+"px_solid_black").crop(action))
                .imageTag(url[url.length-1])).split("'");

        return superUrl[1];
    }

    public String createUrlSuper(String name, int width, int height, String action, String effect){
        String[] url = name.split("/");
        String[] superUrl =  (cloudinary.url()
                .transformation(new Transformation().width(width).height(height).crop(action).effect(effect))
                .imageTag(url[url.length-1])).split("'");

        return superUrl[1];
    }

    public String createUrlSuper(String name, int width, int height, String effect){
        String[] url = name.split("/");
        String[] superUrl =  (cloudinary.url()
                .transformation(new Transformation().width(width).height(height).effect(effect))
                .imageTag(url[url.length-1])).split("'");

        return superUrl[1];
    }

    public String createUrlSuper(String name, int height){
        String[] url = name.split("/");
        String[] superUrl =  (cloudinary.url()
                .transformation(new Transformation().height(height).crop("fit"))
                .imageTag(url[url.length-1])).split("'");

        return superUrl[1];
    }

    public String createUrlSuperMeme(String name, int width, int height, String action, String effect, int border, String text, int fontSize, int scale, int x, int y){

        Map textParams = ObjectUtils.asMap(
                "public_id", "dark_name",
                "font_family", "Arial",
                "font_size", fontSize,
                "font_color", "black",
                "opacity", "90"
        );
        Map textResult= ObjectUtils.emptyMap();
        try {
            textResult = cloudinary.uploader().text(text, textParams);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String[] urlText = textResult.get("url").toString().split("/");
        System.out.println(urlText[urlText.length-1]);
        String[] urlTextFinish = urlText[urlText.length-1].split("\\.");
        for (int count = 0; count < urlTextFinish.length; count ++){
            System.out.println(urlTextFinish[count]);
        }


        String[] url = name.split("/");
        String[] superUrl =  (cloudinary.url()
                .transformation(new Transformation()
                        .width(width*scale).height(height*scale).border(border+"px_solid_black").crop(action).effect(effect))
                .imageTag(url[url.length-1])).split("'");

        String[] superUrlText= (cloudinary.url()
                .transformation(new Transformation()
                        .overlay("text:"+urlTextFinish[0]+":"+text).x(x).y(y).gravity("south_east"))
                .imageTag(superUrl[1], ObjectUtils.emptyMap())).split("'");
        System.out.println(cloudinary.url()
                .transformation(new Transformation()
                        .overlay("text:"+urlTextFinish[0]+":"+text).x(x).y(y).gravity("south_east"))
                .imageTag(superUrl[1], ObjectUtils.emptyMap()));
        // return superUrlText[1];

        String[] superUrl3 =  (cloudinary.url()
                .transformation(new Transformation()
                        .width(width*scale).height(height*scale).border(border+"px_solid_black").crop(action).effect(effect))
                .transformation(new Transformation()
                        .overlay("text:"+urlTextFinish[0]+":"+text).x(x).y(y).gravity("south_east"))
                .imageTag(superUrl[1], ObjectUtils.emptyMap())).split("'");

        Transformation overlay = new Transformation()
                .overlay("text:"+urlTextFinish[0]+":"+text).x(x).y(y).gravity("south_east");

        Transformation image = new Transformation()
                .width(width*scale).height(height*scale).border(border+"px_solid_black").crop(action).effect(effect);
        String[] testImage = (cloudinary.url().transformation(image).imageTag(url[url.length-1])).split("'");
        String[] testImageFinal = testImage[1].split("/");
        String[] testOverlay = (cloudinary.url().transformation(overlay).imageTag(url[url.length-1])).split("'");
        String[] testOverlayFinal = testOverlay[1].split("/");
        String[] test = (cloudinary.url().transformation(image).transformation(overlay).imageTag(url[url.length-1])).split("'");
        String builder = "";
        for (int count = 0; count < testImageFinal.length; count++){
            System.out.println(count + " " + testImageFinal[count]);
            System.out.println(count + " " + testOverlayFinal[count]);
        }
        builder = testOverlayFinal[0]+ "/"+"/"+testOverlayFinal[2]+"/"+testOverlayFinal[3]+"/"+testOverlayFinal[4]+"/"+testOverlayFinal[5]+"/"+testImageFinal[6]+"/"+testOverlayFinal[6]+"/"+testOverlayFinal[7];
        return builder;

    }
}