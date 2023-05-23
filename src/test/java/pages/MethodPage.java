package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.Set;

import static tests.ReusableMethods.waitFor;

public class MethodPage {



    public MethodPage() {
    }

    public static void SamsungUrunArama() {
        BasePage basePage = new BasePage();
        basePage.searchBox.sendKeys("samsung");
        //‘ara' butonuna tıklayacak
        basePage.araButonu.click();

    }

    public static void CerezleriKabulEt (){
        BasePage basePage = new BasePage();
        basePage.kabulEtButonu.click();
    }

    public static void AnasayfaAc(){
        HomePage homePage = new HomePage();
        Driver.getDriver().get(ConfigReader.getProperty("hepsiburadaUrl"));
    }

    public static void BegendiklerimeTikla(){
        Actions actions = new Actions(Driver.getDriver());
        DetailPage detailPage = new DetailPage();
        actions.click(detailPage.hesabimButonu)
                .click(detailPage.begendiklerimButonu).perform();
    }

    public static void PopupKontrolEt(String istenilenAlert){
        String actualAlert = Driver.getDriver().findElement(By.xpath("//div[@class='hb-toast-text']")).getText();
        String expectedAlert= istenilenAlert;
        Assert.assertTrue(actualAlert.contains(expectedAlert));
    }

    public static void BegeneTikla(){
        DetailPage detailPage = new DetailPage();
        waitFor(5);
        detailPage.begenButonu.click();
    }

    public static void YeniAcilanSayfayaGec(){
        String ilkSayfaWindowHandleDegeri=Driver.getDriver().getWindowHandle();
        String ikinciSayfaWindowHandleDegeri="";
        Set<String> windowHandleseti= Driver.getDriver().getWindowHandles();
        for (String each: windowHandleseti
        ) {
            if (!each.equals(ilkSayfaWindowHandleDegeri)){
                ikinciSayfaWindowHandleDegeri=each;
            }
        }
        Driver.getDriver().switchTo().window(ikinciSayfaWindowHandleDegeri);
    }


    public static void SepetimeTikla(){
        DetailPage detailPage = new DetailPage();
        detailPage.sepetimButonu.click();
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("sepetim"));
    }

    public static void BegendiklerimKutusunuBosalt(){
        BegendiklerimeTikla();
        YeniAcilanSayfayaGec();
        DetailPage detailPage = new DetailPage();
        detailPage.duzenleButonu.click();
        detailPage.tumunuSilButonu.click();
        detailPage.secilenleriSilButonu.click();
        detailPage.hepsiburadaIkonu.click();
    }

}
