package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class PoliticasDePrivacidad extends AppCompatActivity {
    FragmentContainerView fragmentContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas_de_privacidad);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Se pondra el idioma 1 como predeterminado (español)
        cambiarPorIdioma(1);

        // Si se presiona uno de los botones que conforman el radio Group
        ((RadioGroup) findViewById(R.id.idiomas)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Dependiendo de lo selecionado se realizara una operacion u otra
                if (checkedId==R.id.radioButtonEspano){         // Si se seleciona el español
                    // Cargara el idioma español (1)
                    cambiarPorIdioma(1);
                }else if(checkedId==R.id.radioButtonIngles){    // Si se seleciona el ingles
                    // Se cargara el idioma ingles (2)
                    cambiarPorIdioma(2);
                } else if (checkedId==R.id.radioButtonChino) {  // Si se seleciona el chino
                    // Se cargara el idioma chino (3)
                    cambiarPorIdioma(3);
                }
            }
        });
    }

    /**
     * Meotodo que pasa la informacion a sus respectivos campos para que sean visualizados de forma correcta
     * @param selecionIdioma
     * @param TyCdUTitulo
     * @param TyCdUInfo
     * @param CdUTitulo
     * @param CdUInfo
     * @param pTitulo
     * @param pInfo
     * @param UaTitulo
     * @param UaInfo
     * @param ctycTitulo
     * @param ctycInfo
     * @param cTitulo
     * @param cInfo
     */
    void setText(String selecionIdioma, String TyCdUTitulo, String TyCdUInfo, String CdUTitulo, String CdUInfo, String pTitulo, String pInfo, String UaTitulo, String UaInfo, String ctycTitulo, String ctycInfo, String cTitulo, String cInfo){
        TextView seleccionarPolitica = findViewById(R.id.textViewTituloSeleccionarIdioma);
        TextView sTyCdUTitulo = findViewById(R.id.TyCdUTitulo);
        TextView sTyCdUInfo = findViewById(R.id.TyCdUInfo);
        TextView sCdUTitulo = findViewById(R.id.CdUTitulo);
        TextView sCdUInfo = findViewById(R.id.CdUInfo);
        TextView spTitulo = findViewById(R.id.pTitulo);
        TextView spInfo = findViewById(R.id.pInfo);
        TextView sUaTitulo = findViewById(R.id.UaTitulo);
        TextView sUaInfo = findViewById(R.id.UaInfo);
        TextView sctycTitulo = findViewById(R.id.ctycTitulo);
        TextView sctycInfo = findViewById(R.id.ctycInfo);
        TextView scTitulo = findViewById(R.id.cTitulo);
        TextView scInfo = findViewById(R.id.cInfo);

        seleccionarPolitica.setText(selecionIdioma);
        sTyCdUTitulo.setText(TyCdUTitulo);
        sTyCdUInfo.setText(TyCdUInfo);
        sCdUTitulo.setText(CdUTitulo);
        sCdUInfo.setText(CdUInfo);
        spTitulo.setText(pTitulo);
        spInfo.setText(pInfo);
        sUaTitulo.setText(UaTitulo);
        sUaInfo.setText(UaInfo);
        sctycTitulo.setText(ctycTitulo);
        sctycInfo.setText(ctycInfo);
        scTitulo.setText(cTitulo);
        scInfo.setText(cInfo);
    }

    /**
     * Metodo encargado de poner un idioma u otro dependiendo de lo que se le pase contiene el texto de todos los idiomas
     * @param i
     */
    void cambiarPorIdioma(int i){
        switch (i){
            case 1:
                setText("Seleccione un idioma para leer los terminos y condiciones",
                        "Términos y Condiciones de Uso",
                        "Por favor, lea estos términos y condiciones cuidadosamente antes de usar la aplicación móvil PDANotes.\n" +
                                "\n" +
                                "Su acceso y uso de la aplicación están condicionados a su aceptación y cumplimiento de estos Términos. Estos Términos se aplican a todos los visitantes, usuarios y otras personas que acceden o usan la aplicación.\n" +
                                "\n" +
                                "Al acceder o utilizar la aplicación, usted acepta estar sujeto a estos Términos. Si no está de acuerdo con alguna parte de los términos, entonces no puede acceder a la aplicación.",
                        "Cuentas de Usuario",
                        "- Al registrarse para usar la aplicación, usted acepta proporcionar información veraz, completa y actualizada sobre usted según sea solicitado en el formulario de registro.\n\n" +
                                "- Usted es responsable de mantener la seguridad de su cuenta y contraseña. No compartas tu contraseña con terceros y notifícanos inmediatamente sobre cualquier actividad no autorizada en tu cuenta.\n\n" +
                                "- Nos reservamos el derecho de suspender o cancelar su cuenta en cualquier momento si consideramos que ha violado estos Términos.",
                        "Privacidad",
                        "Su privacidad es importante para nosotros. Por ello encriptamos toda su información introducida en la aplicación",
                        "Uso Aceptable",
                        "Usted acepta usar la aplicación solo para fines legales y de acuerdo con estos Términos. No debe utilizar la aplicación de ninguna manera que pueda causar daño a la aplicación o a personas o entidades relacionadas con la aplicación.",
                        "Cambios en los Términos",
                        "Nos reservamos el derecho, a nuestra sola discreción, de modificar o reemplazar estos Términos en cualquier momento. Si una revisión es material, intentaremos proporcionar al menos 30 días de aviso antes de que entren en vigencia los nuevos términos.",
                        "Contacto",
                        "Si tiene alguna pregunta sobre estos Términos, póngase en contacto con nosotros.");
            break;
            case 2:
                setText("Please select a language to read the terms and conditions.",
                        "Terms and Conditions",
                        "Please read these terms and conditions carefully before using the PDANotes mobile application.\n" +
                                "\n" +
                                "Your access to and use of the App is conditioned on your acceptance of and compliance with these Terms. These Terms apply to all visitors, users and others who access or use the App.\n" +
                                "\n" +
                                "By accessing or using the App, you agree to be bound by these Terms. If you do not agree to any part of the Terms, then you may not access the App.",
                        "User Accounts",
                        "- By registering to use the application, you agree to provide true, complete and updated information about yourself as requested on the registration form.\n\n" +
                                "- You are responsible for maintaining the security of your account and password. Do not share your password with third parties and notify us immediately of any unauthorized activity on your account.\n\n" +
                                "- We reserve the right to suspend or terminate your account at any time if we believe that you have violated these Terms.",
                        "Privacy",
                        "Your privacy is important to us. That's why we encrypt all your information entered in the application.",
                        "Acceptable Use",
                        "You agree to use the App only for lawful purposes and in accordance with these Terms. You must not use the App in any way that may cause harm to the App or to persons or entities associated with the App.",
                        "Changes in Terms",
                        "We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a revision is material, we will attempt to provide at least 30 days' notice before the new terms become effective.",
                        "Contact",
                        "If you have any questions about these Terms, please contact us.");
            break;
            case 3:
                setText("请选择一种语言阅读条款和条件。",
                        "使用条款和条件",
                        "在使用 PDANotes 移动应用程序之前，请仔细阅读这些条款和条件。\n" +
                                "\n" +
                                "您访问和使用App的条件是您接受并遵守这些条款。这些条款适用于访问或使用App的所有访问者、用户和其他人。\n" +
                                "\n" +
                                "访问或使用App即表示您同意受本条款约束。如果您不同意本条款的任何部分，则不得访问。",
                        "用户账户",
                        "- 在注册使用应用程序时，您同意按照注册表上的要求提供真实、完整和最新的个人信息。\n\n" +
                                "- 您有责任维护账户和密码的安全。请勿与第三方共享密码，如果您的账户出现任何未经授权的活动，请立即通知我们。\n\n" +
                                "- 如果我们认为您违反了这些条款，我们保留随时暂停或终止您的帐户的权利。",
                        "隐私权",
                        "我们非常重视您的隐私。因此，我们会对您在应用程序中输入的所有信息进行加密。",
                        "可接受的使用",
                        "您同意僅將App用於合法目的並遵守本條款。您不得以任何可能对App或与App相关的个人或实体造成损害的方式使用App。",
                        "条款变更",
                        "我们保留随时自行修改或替换这些条款的权利。如果修订是实质性的，我们将尽量在新条款生效前至少 30 天发出通知。",
                        "联系方式",
                        "如果您对这些条款有任何疑问，请联系我们。");
            break;
        }
    }
}