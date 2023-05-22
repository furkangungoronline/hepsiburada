package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class BasePage {

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//input[@type='text']")
    public WebElement searchBox;

    @FindBy(xpath = "//div[@class='searchBoxOld-yDJzsIfi_S5gVgoapx6f']")
    public WebElement araButonu;

    @FindBy(xpath = "//div[text()='Telefon']")
    public WebElement telefonButonu;

    @FindBy(xpath = "//div[text()='Cep Telefonu']")
    public WebElement cepTelefonuButonu;

    @FindBy(xpath = "//h1[@class='searchResultSummaryBar-AVnHBWRNB0_veFy34hco']")
    public WebElement samsungSonuc;

    @FindBy(xpath = "//button[text()='Daha fazla ürün göster']")
    public WebElement dahaFazlaUrunGoster;

    @FindBy(xpath = "(//h3[@data-test-id='product-card-name'])[5]")
    public WebElement besinciUrunResmi;

    @FindBy(xpath = "//button[text()='Kabul et']")
    public WebElement kabulEtButonu;


}
