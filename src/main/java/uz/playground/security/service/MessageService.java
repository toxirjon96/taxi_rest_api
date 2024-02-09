package uz.playground.security.service;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.MessageKey;
import uz.playground.security.entity.Message;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.helper.SecurityHelper;
import uz.playground.security.repository.MessageRepository;
import uz.playground.security.security.UserPrincipal;
import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ResponseHelper responseHelper;

    public MessageService(MessageRepository messageRepository,
                          ResponseHelper responseHelper) {
        this.messageRepository = messageRepository;
        this.responseHelper = responseHelper;
    }
    @PostConstruct
    public void initialize(){
        if (messageRepository.findAll(Pageable.ofSize(13)).isEmpty()){
            List<Message> messageList = new LinkedList<>();

            messageList.add(new Message(MessageKey.UNAUTHORIZED, Lang.EN, "Invalid Token"));
            messageList.add(new Message(MessageKey.UNAUTHORIZED, Lang.UZ, "Noto'g'ri kalit"));
            messageList.add(new Message(MessageKey.UNAUTHORIZED, Lang.RU, "Не верный ключ"));

            messageList.add(new Message(MessageKey.USER_DOES_NOT_EXIST, Lang.EN, "User does not exist"));
            messageList.add(new Message(MessageKey.USER_DOES_NOT_EXIST, Lang.RU, "Пользователь не существует"));
            messageList.add(new Message(MessageKey.USER_DOES_NOT_EXIST, Lang.UZ, "Foydalanuvchi mavjud emas"));

            messageList.add(new Message(MessageKey.INCORRECT_LOGIN_OR_PASSWORD, Lang.EN, "Incorrect Login or Password"));
            messageList.add(new Message(MessageKey.INCORRECT_LOGIN_OR_PASSWORD, Lang.UZ, "Login yoki Parol noto'g'ri"));
            messageList.add(new Message(MessageKey.INCORRECT_LOGIN_OR_PASSWORD, Lang.RU, "Неправильный логин или пароль"));

            messageList.add(new Message(MessageKey.SUCCESS, Lang.EN, "Success"));
            messageList.add(new Message(MessageKey.SUCCESS, Lang.UZ, "Muvaffaqiyatli"));
            messageList.add(new Message(MessageKey.SUCCESS, Lang.RU, "Успешно"));

            messageList.add(new Message(MessageKey.ERROR, Lang.EN, "Error"));
            messageList.add(new Message(MessageKey.ERROR, Lang.UZ, "Hatolik"));
            messageList.add(new Message(MessageKey.ERROR, Lang.RU, "Ошибка"));

            messageList.add(new Message(MessageKey.DATA_NOT_FOUND, Lang.EN, "Data not Found"));
            messageList.add(new Message(MessageKey.DATA_NOT_FOUND, Lang.UZ, "Ma'lumotlar topilmai"));
            messageList.add(new Message(MessageKey.DATA_NOT_FOUND, Lang.RU, "Данные не найдены"));

            messageList.add(new Message(MessageKey.INTERNAL_SERVER_ERROR, Lang.EN, "Internal server error, Please contact your administrator."));
            messageList.add(new Message(MessageKey.INTERNAL_SERVER_ERROR, Lang.UZ, "Tizimning ichki xatoligi"));
            messageList.add(new Message(MessageKey.INTERNAL_SERVER_ERROR, Lang.RU, "Внутренняя ошибка сервера"));

            messageList.add(new Message(MessageKey.INVALID_DATA, Lang.EN, "Invalid data"));
            messageList.add(new Message(MessageKey.INVALID_DATA, Lang.UZ, "Noto'g'ri ma'lumotlar"));
            messageList.add(new Message(MessageKey.INVALID_DATA, Lang.RU, "Неправильные данные"));

            messageList.add(new Message(MessageKey.NUMBER_EXISTS, Lang.EN, "Phone number is already exist!"));
            messageList.add(new Message(MessageKey.NUMBER_EXISTS, Lang.UZ, "Bunday nomerli foydalanuvchi mavjud!"));
            messageList.add(new Message(MessageKey.NUMBER_EXISTS, Lang.RU, "Phone number is already exist!"));

            messageList.add(new Message(MessageKey.EMAIL_EXISTS, Lang.EN, "Email is already taken!"));
            messageList.add(new Message(MessageKey.EMAIL_EXISTS, Lang.UZ, "Email is already taken!"));
            messageList.add(new Message(MessageKey.EMAIL_EXISTS, Lang.RU, "Email is already taken!"));

            messageList.add(new Message(MessageKey.LIST_EMPTY, Lang.EN, "List is empty!"));
            messageList.add(new Message(MessageKey.LIST_EMPTY, Lang.UZ, "List bo'sh!"));
            messageList.add(new Message(MessageKey.LIST_EMPTY, Lang.RU, "List is empty!"));

            messageList.add(new Message(MessageKey.INCORRECT_SECRET_KEY, Lang.EN, "Incorrect secret key!"));
            messageList.add(new Message(MessageKey.INCORRECT_SECRET_KEY, Lang.UZ, "Maxfiy kalit noto'g'ri!"));
            messageList.add(new Message(MessageKey.INCORRECT_SECRET_KEY, Lang.RU, "Incorrect secret key!"));

            messageList.add(new Message(MessageKey.PROHIBITED_USER, Lang.EN, "You are not valid user!"));
            messageList.add(new Message(MessageKey.PROHIBITED_USER, Lang.UZ, "Foydalanuvchi huquqi cheklangan!"));
            messageList.add(new Message(MessageKey.PROHIBITED_USER, Lang.RU, "You are not valid user!"));

            messageList.add(new Message(MessageKey.EXIST_CAR, Lang.EN, "This car exists!"));
            messageList.add(new Message(MessageKey.EXIST_CAR, Lang.UZ, "Bunday avtomobil mavjud!"));
            messageList.add(new Message(MessageKey.EXIST_CAR, Lang.RU, "This car exists!"));

            messageRepository.saveAll(messageList);
        }
    }
    public String getMessage(String key){
        UserPrincipal user = SecurityHelper.getUser();
        Lang lang = Objects.isNull(user) ? Lang.UZ : user.getLang();
        return messageRepository.findByKeyAndLang(key, lang)
                .map(Message::getMessage)
                .orElse(key);
    }
    public ResponseEntity<?> editMessage(Message message){
        Optional<Message> messageOpt = messageRepository.findByKeyAndLang(message.getKey(), message.getLang());
        if (messageOpt.isEmpty()){
            return responseHelper.noDataFound();
        }
        Message msg = messageOpt.get();
        msg.setKey(message.getKey());
        msg.setLang(message.getLang());
        msg.setMessage(message.getMessage());
        messageRepository.save(msg);
        return responseHelper.success();
    }
    public ResponseEntity<?> deleteMessage(Long messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isEmpty()){
            return responseHelper.noDataFound();
        }
        messageRepository.delete(message.get());
        return responseHelper.success();
    }
}