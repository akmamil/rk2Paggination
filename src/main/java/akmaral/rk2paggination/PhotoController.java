package akmaral.rk2paggination;



import akmaral.rk2paggination.Photo;
import akmaral.rk2paggination.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PhotoController {

    private final EmailService emailService;

    @Autowired
    public PhotoController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/photos")
    public String getPhotos(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        File folder = new File("src/main/resources/static/photo");


        if (!folder.exists() || !folder.isDirectory()) {
            model.addAttribute("error", "Folder not found or invalid.");
            return "errorPage";
        }

        File[] files = folder.listFiles();


        if (files == null || files.length == 0) {
            model.addAttribute("error", "No photos found.");
            return "errorPage";
        }

        String commonEmail = "kingzalishev@gmail.com";

        List<Photo> photos = Arrays.stream(files)
                .filter(File::isFile)
                .map(file -> new Photo(file.getName(), "/photo/" + file.getName(), commonEmail))
                .collect(Collectors.toList());

        int totalPhotos = photos.size();
        int totalPages = (int) Math.ceil((double) totalPhotos / size);


        if (page < 0) page = 0;
        if (page >= totalPages) page = totalPages - 1;

        int start = page * size;
        int end = Math.min(start + size, totalPhotos);


        List<Photo> pagePhotos = photos.subList(start, end);


        model.addAttribute("photos", pagePhotos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);


        model.addAttribute("nextPage", page + 1 < totalPages ? page + 1 : page);
        model.addAttribute("prevPage", page > 0 ? page - 1 : 0);

        return "photoGallery";
    }

    @GetMapping("/sendEmails")
    public String sendEmails(Model model) {
        try {

            int numberOfEmails = 10;


            for (int i = 0; i < numberOfEmails; i++) {
                emailService.sendEmail("akmakuroo47@gmail.com", "Work", "Lalala");
            }

            model.addAttribute("message", "Emails sent successfully to " + numberOfEmails + " recipients.");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to send email: " + e.getMessage());
        }

        return "emailStatus";
    }


    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "errorPage";
    }
}