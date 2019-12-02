package discussionComponent;

import authentification.UserConnectionSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.Post;
import models.Thread;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.IOException;

public class ThreadListElement {
    @FXML
    private AnchorPane listElement;
    @FXML
    private Label threadTitle;
    @FXML
    private Label threadStartedOn;
    @FXML
    private Label threadResponseCount;
    @FXML
    private Label threadAuthor;
    @FXML
    private Label threadLastReplyDate;
    @FXML
    private Label threadLastReplyAuthor;
    @FXML
    private Button threadLastReplyBtn;

    private Thread tlo;
    private UserConnectionSingleton con = UserConnectionSingleton.getInstance();
    private EntityManager entityManager = con.getManager();

    public ThreadListElement(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/threadListObject.fxml"));
        fxmlLoader.setController(this);
        try{
            listElement = fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setElement(Thread tlo){

        TypedQuery tq1 = entityManager.createNamedQuery("Thread.getReplyCount", Integer.class);
        tq1.setParameter("tid", tlo.getId());
        this.tlo = tlo;
        threadTitle.setText(tlo.getThreadTitle());
        threadLastReplyAuthor.setText(tlo.getThreadLastPost().getAuthor().getFirstname() + " " + tlo.getThreadLastPost().getAuthor().getLastname().substring(0,1));
        threadResponseCount.setText(tq1.getSingleResult() + " Replies");
        threadLastReplyDate.setText(tlo.getThreadLastPost().getPostTime());
        threadStartedOn.setText("Started by " + tlo.getThreadAuthor().getFirstname() + " " + tlo.getThreadAuthor().getLastname().substring(0,1) + "., " +tlo.getThreadFirstPost().getPostTime());
    }

    @FXML
    private void openPost() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/threadView.fxml"));
        threadViewController tvc = new threadViewController(tlo);
        loader.setController(tvc);
        AnchorPane threadViewAP = loader.load();
        Scene scene = listElement.getScene();
        Pane v = (Pane) scene.lookup("#view");
        v.getChildren().add(threadViewAP);
        v.getChildren().remove(0);
    }

    public AnchorPane getListElement(){
        return listElement;
    }
}