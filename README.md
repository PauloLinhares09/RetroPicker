# RetroPicker - BETA 1

## Economize tempo no desenvolvimento. 

>Trabalhar com Ações de mídias no Android, envolvendo Câmera e Galeria, pode ser um processo tardio para sua produtividade - principalmente por volume de regras que precisam ser seguidas, como: tratar Request Runtime Permissions(Android 6+), respeitar as regras de compartilhamento de arquivos(isso envolve as fotos) com Providers (Android 7+).

>Baseado nisso e muitos outros fatores, essa Lib tenta fazer todo o trabalho mais pesado para que você possa depositar seu tempo apenas nas peculiaridades do seu Aplicativo. Vamos lá?

# Get started

## 1 - Adicione em seu root build.gradle no final dentro da tag repositories:

```
allprojects {
       repositories {
              ...
              maven { url 'https://jitpack.io' }
       }
}
```

## 2 - Adicione a dependência:

```
dependencies {
    compile 'com.github.PauloLinhares09:RetroPicker:1.2.3-Beta1'
}
```


## 3 - Adicionado as configurações acima e feito "sync" em seu projeto, basta chamar o seguinte código em sua ação de botão (Câmera ou Galeria):

> public void yourButtonAction(View view){
```
       Retropicker.Builder builder =  new Retropicker.Builder(this)
                   .setTypeAction(Retropicker.CAMERA_PICKER) //Para abrir a galeria passe Retropicker.GALLERY_PICKER
                   .setImageName("first_image.jpg") //Opicional
                   .checkPermission(true);

           builder.enquee(new CallbackPicker() {
               @Override
               public void onSuccess(Bitmap bitmap, String imagePath) {
                   imageView.setImageBitmap(bitmap); //ImageView do seu aplicativo onde quer exibir a imagem final
               }

               @Override
               public void onFailure(Throwable error) {
                   Log.e("TAG", "error: " + error.getMessage());
                   Log.e("TAG", "error toString: " + error.toString());
               }
           });

           Retropicker retropicker = builder.create();
           retropicker.open();

       }
```
> }


## 4 - Implemente o método onRequestPermissionsResult(...) em sua Activity de contexto deste uso em seu APP:
```
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       //Call this line fir manager RetroPicker Library
       retropicker.onRequesPermissionResult(requestCode, permissions, grantResults);

}
```
NOTA: Observe que estamos usando uma instância global (retropicker) para nossa referência da Library RetroPicker. Portanto, 
atualize seu código acima para ter esta instância acessível globalmente pela sua Activity ou Fragment.

---------------------------------------------------

OBS: Esta biblioteca está em Beta 1. Portanto, alterações a nível de estrutura ainda podem acontecer. Caso deseje utiliza-la em um projeto já em produção, você deve ter em mente que pode chegar um momento de necessitar mudar sua implementação - ou não.  
