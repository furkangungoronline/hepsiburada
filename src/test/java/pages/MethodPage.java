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

    public static void urunArama(String aranacakUrun) {
        BasePage basePage = new BasePage();
        basePage.searchBox.sendKeys(aranacakUrun);
        basePage.araButonu.click();
    }

    public static void cerezleriKabulEt (){
        BasePage basePage = new BasePage();
        basePage.kabulEtButonu.click();
    }

    public static void anasayfaAc(){
        HomePage homePage = new HomePage();
        Driver.getDriver().get(ConfigReader.getProperty("hepsiburadaUrl"));
        Driver.getDriver().manage().window().maximize();
    }

    public static void begendiklerimeTikla(){
        Actions actions = new Actions(Driver.getDriver());
        DetailPage detailPage = new DetailPage();
        actions.click(detailPage.hesabimButonu)
                .click(detailPage.begendiklerimButonu).perform();
    }

    public static void popupKontrolEt(String istenilenAlert){
        String actualAlert = Driver.getDriver().findElement(By.xpath("//div[@class='hb-toast-text']")).getText();
        String expectedAlert= istenilenAlert;
        //Assert.assertTrue(actualAlert.contains(expectedAlert));
    }

    public static void begeneTikla(){
        DetailPage detailPage = new DetailPage();
        waitFor(5);
        if (detailPage.begenButonu.isSelected()){
            detailPage.begenButonu.click();
            waitFor(3);
            detailPage.begenButonu.click();
        }else{
            detailPage.begenButonu.click();
        }
    }

    public static void yeniAcilanSayfayaGec(){
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


    public static void sepetimeTikla(){
        DetailPage detailPage = new DetailPage();
        detailPage.sepetimButonu.click();
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("sepetim"));
    }

    public static void begendiklerimKutusunuBosalt(){
        begendiklerimeTikla();
        DetailPage detailPage = new DetailPage();
        detailPage.duzenleButonu.click();
        detailPage.tumunuSilButonu.click();
        detailPage.secilenleriSilButonu.click();
        detailPage.onaylaButonu.click();
        detailPage.hepsiburadaIkonu.click();
    }

}
