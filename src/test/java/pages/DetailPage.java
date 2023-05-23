package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class DetailPage {

    public DetailPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//div[@role='presentation'])[1]")
    public WebElement begenButonu;

    @FindBy(xpath = "//span[text()='Hesabım']")
    public WebElement hesabimButonu;

    @FindBy(xpath = "//a[text()='Beğendiklerim']")
    public WebElement begendiklerimButonu;

    @FindBy(xpath = "//button[text()='Düzenle']")
    public WebElement duzenleButonu;

    @FindBy(xpath = "//span[text()='Sepete Ekle']")
    public WebElement sepeteEkleButonu;

    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement tumunuSilButonu;

    @FindBy(xpath = "//div[text()='Seçilenleri sil']")
    public WebElement secilenleriSilButonu;

    @FindBy(xpath = "//a[@title='Hepsiburada']")
    public WebElement hepsiburadaIkonu;

    @FindBy(xpath = "//span[text()='Sepetim']")
    public WebElement sepetimButonu;

    @FindBy(xpath = "//button[text()='Onayla']")
    public WebElement onaylaButonu;

}
