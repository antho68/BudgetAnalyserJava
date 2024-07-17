package org.aba.dto;

import org.aba.BaseUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class BudgetDataDto
{
    public static String PT_PAYPAL = "Paypal";
    public static String PT_CB = "Carte/Apple pay";
    public static String PT_VIRMENT = "Virement";
    public static String PT_PRLV = "PRLV.";

    public static String UF_HOUSE = "Maison";
    public static String UF_ANTHO = "Antho";
    public static String UF_MALO = "Malo";
    public static String UF_NOHAN = "Nohan";
    public static String UF_RIO = "Rio";
    public static String UF_SAVE = "Epargne";
    public static String UF_INSURANCE = "Assurance";
    public static String[] ALL_UF = {UF_HOUSE, UF_ANTHO, UF_MALO, UF_NOHAN
            , UF_RIO, UF_SAVE, UF_INSURANCE};

    String description = "";
    String paymentType = "";
    String usedFor = "";
    Date date = null;
    Double amount = null;

    public BudgetDataDto()
    {

    }

    public void initPaymentType()
    {
        if (getDescription().contains("Paypal"))
        {
            setPaymentType(PT_PAYPAL);
        }
        else if (getDescription().contains("CB") || getDescription().contains("PSC"))
        {
            setPaymentType(PT_CB);

            if (getDescription().contains("CARTE 5424"))
            {
                setUsedFor(UF_ANTHO);
            }
            else if (getDescription().contains("CARTE 6182"))
            {
                setUsedFor(UF_MALO);
            }
        }
        else if (getDescription().contains("VIR") || getDescription().contains("RAPATRIEMENT SALAIRE"))
        {
            setPaymentType(PT_VIRMENT);

            if (getDescription().contains("RAPATRIEMENT SALAIRE"))
            {
                setUsedFor(UF_ANTHO);
            }
            else if (getDescription().contains("VIR SELARL"))
            {
                setUsedFor(UF_MALO);
            }
        }
        else if (getDescription().contains("PRLV"))
        {
            setPaymentType(PT_PRLV);
        }
    }

    public void initUsedFor()
    {
        if (getDescription().contains("Paypal") || getDescription().contains("*ITUNESAP")
                || getDescription().contains(" AMAZON PAYMENTS") || getDescription().contains(" WALDIGHOFE WELDOM")
                || getDescription().contains("AMAZON EU SARL")
                || getDescription().contains("PRLV SEPA FREE TELECOM FHD-1303937822 FREE HAUTDEBIT 1303937822")
                || getDescription().contains("PRLV SEPA ELECTRICITE DE FRANCE Z014219594569")
                || getDescription().contains("MONTBELIARD PETRO BELMONT")
                || getDescription().contains("PRLV SEPA ELECTRICITE DE FRANCE Z014219594569")
                || getDescription().contains("WALDIGHOFEN SARL ECOSTATION")
                || getDescription().contains("VIR SEPA NETFLIX CHRISTOPHE CG3V24187L237350")
                || getDescription().contains("ECH PRET CAP+IN 03130 207942 02")
                || getDescription().contains("PRLV SEPA COMPAGNIE GENERALE DE CGL/T007111713/111870424 C242473736")
                || getDescription().contains("F COTIS EUC.SERENITE")
                || getDescription().contains(
                "PRLV SEPA DIRECTION GENERALE DE 5P087000024G0510876110 PRELEVEMENT A LA SOURCE REVENUS 2024")
                || getDescription().contains(
                "PRLV SEPA DIRECTION GENERALE DE 1E067000068033M368025499680 680333022153036413 111 MENM368025499680033 IMPOT"))
        {
            setUsedFor(UF_HOUSE);
        }
        else if (getDescription().contains(
                "ASSURANCE ANIMAUX DE COMPAGNIE RMS24179266041415GI ASSURANCE ANIMAUX DE COMPAGNIE S11041640"))
        {
            setUsedFor(UF_RIO);
        }
        else if (getDescription().contains(
                "PRLV SEPA ORANGE SA 166241758995465173919119913550 VOTRE ABONNEMENT MOBILE: 06XXXXX232")
                || getDescription().contains("VIR ARGENT POUR LE MOIS ANTHO")
                || getDescription().contains("PRLV SEPA URSSAF FRANCHE-COMTE-")
                || getDescription().contains("PRLV SEPA GIEPS-GIE DE PREVOYAN PREL.APRS 072024 6902R02644TP"))
        {
            setUsedFor(UF_ANTHO);
        }
        else if (getDescription().contains("VIR ARGENT POUR LE MOIS MALO")
                || getDescription().contains("PRLV SEPA FREE MOBILE FMPMT")
                || getDescription().contains("MULHOUSE SODEXO SSAUVEUR")
                || getDescription().contains("PRLV SEPA GIEPS-GIE DE PREVOYAN PREL.APRS 072024 6902S02691TP"))
        {
            setUsedFor(UF_MALO);
        }
        else if (getDescription().contains("CENTRE PAJEMPLOI"))
        {
            setUsedFor(UF_NOHAN);
        }
        else if (getDescription().contains("PRLV SEPA AXA I0000787146718 I0000787146718-CONTRAT0000021700737904")
                || getDescription().contains("PRLV SEPA AXA I0000787146750 I0000787146750-CONTRAT0000021700724704")
                || getDescription().contains("PRLV SEPA AXA I0000787146780 I0000787146780-CONTRAT0000021700719804")
                || getDescription().contains("PRLV SEPA AXA I0000787146866 I0000787146866-CONTRAT0000021681176204")
                || getDescription().contains("PLAN ASSUR VIE OY000014652865 2418799 OY000014652865"))
        {
            setUsedFor(UF_INSURANCE);
        }
        else if (getDescription().contains("PRLV SEPA ADIS AGIPI EPARGNE RETRAITE 0010426526 0010426526")
                || getDescription().contains("PRLV SEPA ADIS AGIPI EPARGNE RETRAITE 0010426457 0010426457")
                || getDescription().contains("VIR PLACEMENT POUR NOHAN CG3V24187L254521")
                || getDescription().contains("VIR VIREMENT REMPLA. REGINE CG3V24187L237351"))
        {
            setUsedFor(UF_SAVE);
        }
    }

    public String getDescriptionRecap()
    {
        String recap = getDescription();

        if (recap.equals("ECH PRET CAP+IN 03130 207942 02"))
        {
            return "Prêt Maison";
        }
        else if (recap.equals("PAIEMENT CB 0807 LUXEMBOURG PAYPAL *ADOBESYS CARTE 5424"))
        {
            return "Abo Lightroom/Photoshop";
        }
        else if (recap.equals(
                "ASSURANCE ANIMAUX DE COMPAGNIE RMS24179266041415GI ASSURANCE ANIMAUX DE COMPAGNIE S11041640"))
        {
            return "Assu Santé RIO";
        }
        else if (recap.contains(
                "PRLV SEPA ORANGE SA 166241758995465173919119913550 VOTRE ABONNEMENT MOBILE: 06XXXXX232"))
        {
            return "Abo Tel Antho";
        }
        else if (recap.contains("ALTKIRCH LIDL"))
        {
            return "ALTKIRCH LIDL";
        }
        else if (recap.contains("HIRSINGUE LECLERC"))
        {
            return "HIRSINGUE LECLERC";
        }
        else if (recap.contains("ALTKIRCH E.LECLERC WEB"))
        {
            return "ALTKIRCH DRIVE LECLERC";
        }
        else if (recap.contains("HIRSINGUE DRIVE E.LECLERC"))
        {
            return "HIRSINGUE DRIVE LECLERC";
        }
        else if (recap.contains("VIR ARGENT POUR LE MOIS MALO"))
        {
            return "ARGENT MOIS MALO";
        }
        else if (recap.contains("VIR ARGENT POUR LE MOIS ANTHO"))
        {
            return "ARGENT MOIS ANTHO";
        }
        else if (recap.contains("PRLV SEPA FREE MOBILE FMPMT"))
        {
            return "Abo Tel Malo";
        }
        else if (recap.contains("PRLV SEPA URSSAF FRANCHE-COMTE-"))
        {
            return "SECU Antho";
        }
        else if (recap.contains("*ITUNESAP"))
        {
            return "Abo. Apple Music/Cloud/..";
        }
        else if (recap.contains(" WALDIGHOFE WELDOM"))
        {
            return "Weldom Waldig";
        }
        else if (recap.contains("AMAZON EU SARL") || recap.contains("AMAZON PAYMENTS"))
        {
            return "Amazon";
        }
        else if (recap.contains("CENTRE PAJEMPLOI"))
        {
            return "PAJEMPLOI MAM";
        }
        else if (recap.contains("MULHOUSE SODEXO SSAUVEUR"))
        {
            return "Cantine Malo";
        }
        else if (recap.contains("PRLV SEPA ADIS AGIPI EPARGNE RETRAITE 0010426526 0010426526"))
        {
            return "PER Antho axa";
        }
        else if (recap.contains("PRLV SEPA ADIS AGIPI EPARGNE RETRAITE 0010426457 0010426457"))
        {
            return "PER Malo axa";
        }
        else if (recap.contains("PRLV SEPA AXA I0000787146718 I0000787146718-CONTRAT0000021700737904"))
        {
            return "Assu. BMW";
        }
        else if (recap.contains("PRLV SEPA AXA I0000787146750 I0000787146750-CONTRAT0000021700724704"))
        {
            return "Assu. Kangoo";
        }
        else if (recap.contains("PRLV SEPA AXA I0000787146780 I0000787146780-CONTRAT0000021700719804"))
        {
            return "Assu. Kia";
        }
        else if (recap.contains("PRLV SEPA AXA I0000787146866 I0000787146866-CONTRAT0000021681176204"))
        {
            return "Assu. Maison";
        }
        else if (recap.contains("PRLV SEPA AXA FRANCE VIE SA I0000785094920 I0000785094920-CONTRAT0000021703135704"))
        {
            return "Assu. Accident de la vie";
        }
        else if (recap.contains("PRLV SEPA COMPAGNIE GENERALE DE CGL/T007111713/111870424 C242473736"))
        {
            return "Prêt Kia";
        }
        else if (recap.contains("VIR PLACEMENT POUR NOHAN CG3V24187L254521"))
        {
            return "Livret bleu Nohan";
        }
        else if (recap.contains("PRLV SEPA FREE TELECOM FHD-1303937822 FREE HAUTDEBIT 1303937822"))
        {
            return "Abo. Free Fibre";
        }
        else if (recap.contains("PLAN ASSUR VIE OY000014652865 2418799 OY000014652865"))
        {
            return "Assu. vie";
        }
        else if (recap.contains("MONTBELIARD PETRO BELMONT"))
        {
            return "Essence ALtkirch LECLER";
        }
        else if (recap.contains("PRLV SEPA GIEPS-GIE DE PREVOYAN PREL.APRS 072024 6902R02644TP"))
        {
            return "Assu. Santé Antho";
        }
        else if (recap.contains("PRLV SEPA GIEPS-GIE DE PREVOYAN PREL.APRS 072024 6902S02691TP"))
        {
            return "Assu. Santé Malo";
        }
        else if (recap.contains("PRLV SEPA ADIS AGIPI PREVOYANCE 0070741036 0070741036"))
        {
            return "Assu. Prêt Maison Antho";
        }
        else if (recap.contains("PRLV SEPA ADIS AGIPI PREVOYANCE 0070746891 0070746891"))
        {
            return "Assu. Prêt Maison Malo";
        }
        else if (recap.contains("HUNDSBACH SUMUP *CHEZ MAX"))
        {
            return "Pizza MAX";
        }
        else if (recap.contains("WALDIGHOFEN SARL ECOSTATION"))
        {
            return "Essence ECO Waldig";
        }
        else if (recap.contains("PRLV SEPA ELECTRICITE DE FRANCE Z014219594569"))
        {
            return "ELEC EDF";
        }
        else if (recap.contains("WALDIGHOFEN SARL ECOSTATION"))
        {
            return "Essence ECO Waldig";
        }
        else if (recap.contains("VIR SEPA NETFLIX CHRISTOPHE CG3V24187L237350"))
        {
            return "Netflix Christophe";
        }
        else if (recap.contains("VIR VIREMENT REMPLA. REGINE CG3V24187L237351"))
        {
            return "Ancien VIR Régine";
        }
        else if (recap.contains("F COTIS EUC.SERENITE"))
        {
            return "Frais tenu de compte";
        }
        else if (recap.contains(
                "PRLV SEPA DIRECTION GENERALE DE 5P087000024G0510876110 PRELEVEMENT A LA SOURCE REVENUS 2024"))
        {
            return "Impot sur revenu";
        }
        else if (recap.contains(
                "PRLV SEPA DIRECTION GENERALE DE 1E067000068033M368025499680 680333022153036413 111 MENM368025499680033 IMPOT"))
        {
            return "Impot foncier";
        }

        recap = recap.replace("PRLV SEPA ", "");
        recap = recap.replace("PAIEMENT CB ", "");
        recap = recap.replace("VIR SELARL AMIM VIR ", "");
        recap = recap.replace("VIR SELARL AMIM AMIM VIR ", "");
        recap = recap.replace("PAIEMENT PSC ", "");
        recap = recap.replace("VIR SEPA ", "");
        recap = recap.replace(" LUXEMBOURG PAYPAL", "");

        return recap;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public String getInfos()
    {
        String s = "";
        if (getDate() != null)
        {
            s += BaseUtils.getDateFormatted(getDate()) + ", ";
        }

        s += getDescription();

        if (getAmount() != null)
        {
            s += " :" + BaseUtils.getDoubleFormatted(getAmount());
        }

        return s;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getUsedFor()
    {
        return usedFor;
    }

    public void setUsedFor(String usedFor)
    {
        this.usedFor = usedFor;
    }
}
