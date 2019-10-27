import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;

public class Sql {
    Sql2o sql2o = new Sql2o("jdbc:h2:~/practica3", "sa", "");
    public void insertAdmin () throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String insertQuery =
                "INSERT INTO USUARIO (username, password, administrador, autor, nombre) " +
                        "VALUES (:username, :password, :administrador, :autor, :nombre)";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(insertQuery)
                    .addParameter("username", "admin")
                    .addParameter("password", "1234")
                    .addParameter("administrador", true)
                    .addParameter("autor", true)
                    .addParameter("nombre", "cristian")
                    .executeUpdate();
            con.commit();
        }
    }

    public void insertUser (Usuario usuario) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String insertQuery =
                "INSERT INTO USUARIO (username, password, administrador, autor, nombre) " +
                        "VALUES (:username, :password, :administrador, :autor, :nombre)";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(insertQuery)
                    .addParameter("username", usuario.getUsername())
                    .addParameter("password", usuario.getPassword())
                    .addParameter("administrador", usuario.isAdministrador())
                    .addParameter("autor", usuario.isAutor())
                    .addParameter("nombre", usuario.getNombre())
                    .executeUpdate();
            con.commit();
        }
    }

    public void insertArticulo (Articulo articulo) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String insertQuery =
                "INSERT INTO ARTICULO (titulo, cuerpo, usuario_id, fecha) " +
                        "VALUES (:titulo, :cuerpo, :usuario_id, :fecha)";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(insertQuery)
                    .addParameter("titulo", articulo.getTitulo())
                    .addParameter("cuerpo", articulo.getCuerpo())
                    .addParameter("usuario_id", articulo.getAutor().id)
                    .addParameter("fecha", articulo.getFecha())
                    .executeUpdate();
            con.commit();
        }

    }

    public void insertEtiqueta (Etiqueta etiqueta, List tag, int cant) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        for(int i=0; i < cant; i++){
            String insertQuery =
                    "INSERT INTO ETIQUETA (etiqueta, articulo_id) " +
                            "VALUES (:etiqueta, :articulo_id)";

            try (Connection con = sql2o.beginTransaction()) {
                con.createQuery(insertQuery)
                        .addParameter("etiqueta", tag.get(i))
                        .addParameter("articulo_id", etiqueta.getArticulo_id())
                        .executeUpdate();
                con.commit();
            }
        }
    }
    public void deleteEtiqueta (int articulo_id) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("delete from etiqueta where articulo_id="+articulo_id).executeUpdate();
            con.commit();
        }

    }

    public void deleteArticulo (int articulo_id) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("delete from comentario where articulo_id="+articulo_id).executeUpdate();
            con.createQuery("delete from etiqueta where articulo_id="+articulo_id).executeUpdate();
            con.createQuery("delete from articulo where id="+articulo_id).executeUpdate();
            con.commit();
        }

    }

    public void insertComentario (Comentario comentario) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String insertQuery =
                "INSERT INTO COMENTARIO (comentario, usuario_id, articulo_id) " +
                        "VALUES (:comentario, :usuario_id, :articulo_id)";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(insertQuery)
                    .addParameter("comentario", comentario.getComentario())
                    .addParameter("usuario_id", comentario.getAutor().id)
                    .addParameter("articulo_id", comentario.getArticulo_id())
                    .executeUpdate();
            con.commit();
        }

    }

    public List<Usuario> getUser(int id){
        String sql =
                "SELECT * FROM usuario " + "WHERE id="+id;

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public List<Usuario> getadmin(){
        String sql =
                "SELECT * FROM usuario WHERE username = 'admin'";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public List<Usuario> getAllUsers(){
        String sql =
                "SELECT id, username, password, administrador, autor, nombre " +
                        "FROM usuario";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public List<Articulo> getAllArticles(){
        try (Connection con = sql2o.open()) {
            List<Articulo> articulos = con.createQuery("Select * from Articulo").executeAndFetch(Articulo.class);
            for(Articulo articulo: articulos)
            {
                articulo.setAutor(getUser(articulo.usuario_id).get(0));
            }
            return articulos;
        }
    }

    public List<Articulo> getArticle(int id){
        id+=1;
        try (Connection con = sql2o.open()) {
            List<Articulo> articulos = con.createQuery("Select * from Articulo where id="+id).executeAndFetch(Articulo.class);
            for(Articulo articulo: articulos)
            {
                articulo.setAutor(getUser(articulo.usuario_id).get(0));
                articulo.setListaEtiqueta(getAllEtiquetas(id));
                articulo.setListaComentario(getAllComments(id));
            }
            return articulos;
        }
    }

    public List<Articulo> getLastArticles(){
        try (Connection con = sql2o.open()) {
            List<Articulo> articulos = con.createQuery("Select * from Articulo order by id desc").executeAndFetch(Articulo.class);
            for(Articulo articulo: articulos)
            {
                articulo.setAutor(getUser(articulo.usuario_id).get(0));
            }
            return articulos;
        }
    }

    public Articulo getArticulo(int id){
        String sql =
                "SELECT * " +
                        "FROM articulo WHERE id="+id;

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeScalar(Articulo.class);
        }
    }

    public Integer getLastArticle() {
        String sql =
                "SELECT top 1 id " +
                        "FROM articulo order by id desc";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public void editArticulo(int id, Articulo articulo){
        String updateSql = "update articulo set titulo = :titulo, cuerpo = :cuerpo, fecha = :fecha where id ="+id;

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("titulo", articulo.getTitulo())
                    .addParameter("cuerpo", articulo.getCuerpo())
                    .addParameter("fecha", articulo.getFecha())
                    .executeUpdate();
        }
    }

    public List<Etiqueta> getAllEtiquetas(int id){
        try (Connection con = sql2o.open()) {
            List<Etiqueta> etiquetas = con.createQuery("Select * from etiqueta where articulo_id="+id).executeAndFetch(Etiqueta.class);
            return etiquetas;
        }
    }

    public List<Comentario> getAllComments(int id){
        try (Connection con = sql2o.open()) {
            List<Comentario> comentarios = con.createQuery("Select * from comentario where articulo_id="+id).executeAndFetch(Comentario.class);
            for(Comentario comentario: comentarios)
            {
                comentario.setAutor(getUser(comentario.usuario_id).get(0));
            }
            return comentarios;
        }
    }
}
