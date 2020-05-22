package Project;


import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainShopItemController extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private Label productCount;
    @FXML private TextField productCounter;


    private ShoppingItem shoppingItem;
    private ShoppingCart shoppingCart;
    private Product p;
    private BackendControllerProducts bckEndP;

    public MainShopItemController(Product p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainShopItem.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        bckEndP = BackendControllerProducts.getInstance();
        shoppingCart = bckEndP.getShoppingCart();
        this.p = p;
        shoppingItem = new ShoppingItem(p,1);
        productName.setText(p.getName());
        productImage.setImage(bckEndP.getFXImage(p));
        productPrice.setText(String.valueOf(p.getPrice()) + " kr/" + shoppingItem.getProduct().getUnitSuffix());
        productCount.setText("0.0 " + shoppingItem.getProduct().getUnitSuffix());
        productCounter.setText("0.0 " + shoppingItem.getProduct().getUnitSuffix());

    }

    @FXML
    public void addToCart(){
        if (!isInCart(shoppingItem) ){
            shoppingItem.setAmount(1);
            shoppingCart.addItem(shoppingItem);
        }else {
            shoppingItem.setAmount(shoppingItem.getAmount() + 1);
            shoppingCart.fireShoppingCartChanged(null, false);
        }
        productCount.setText(String.valueOf(shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
        productCounter.setText(String.valueOf(shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
    }

    @FXML
    public void removeFromCart(){
        if (shoppingItem.getAmount() == 0) return;
        shoppingItem.setAmount(shoppingItem.getAmount()-1);
        if (shoppingItem.getAmount() == 0){
            shoppingCart.removeItem(shoppingItem);
        }
        shoppingCart.fireShoppingCartChanged(null, false);
        productCount.setText(String.valueOf(shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
        productCounter.setText(String.valueOf(shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
    }

    private boolean isInCart(ShoppingItem sci){
        for (ShoppingItem i:shoppingCart.getItems()) {
            if (i.equals(sci))return true;
        }
        return false;
    }

}
