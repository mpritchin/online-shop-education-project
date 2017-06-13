package shop.service;

/**
 * Created by toshiba_admin on 23.05.2017.
 */
public interface UserService<K,T> {

    void save(K entity);

    void delete(K entity);

    K getByLoginAndPassword (String login, String password);

    K getById(T id);

    K getUserByLogin(String login);

    void update(K entity);

    boolean listUser();
}
