package Application.Servicies;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileService {

    public static final File FILES_PATH = new File(System.getProperty("user.dir") + "/Images/");

    public static boolean init() {
        File newFile = new File(String.valueOf(FILES_PATH));
        if (!newFile.exists()) {
            return newFile.mkdirs();
        }
        return false;
    }

    public static File saveImage(File newFile, MultipartFile file) {
        newFile = new File(newFile, file.getOriginalFilename());
        if (newFile.exists()) {
            String extension = newFile.getName().substring(newFile.getName().lastIndexOf("."));
            String nameOfFile = UUID.randomUUID().toString().concat(extension);
            newFile = new File((newFile.getParent().concat(File.separator)).concat(nameOfFile));
        }
        try {
            FileOutputStream stream = new FileOutputStream(newFile);
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public static byte[] getBytes(File file) throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return bytes;
    }
}
