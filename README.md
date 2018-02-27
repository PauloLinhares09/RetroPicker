# RetroPicker - BETA 1

## Economize tempo - e porquê não dinheiro. 
> Adicione, com poucas linhas de código, as ações de Galeria e captura de Fotos em seu Aplicativo android.

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
    compile 'com.github.PauloLinhares09:RetroPicker:1.0.2' 
}
```


## 3 - Adicionado as configurações acima e feito "sync" em seu projeto, basta chamar o seguinte código em sua ação de botão (Câmera ou Galeria):

> public void yourButtonAction(View view){
```
       Retropicker.Builder builder =  new Retropicker.Builder(this)
                   .setTypeAction(Retropicker.CAMERA_PICKER) //Para abrir a galeria passe Retropicker.GALLERY_PICKER
                   .setImageName("first_image.jpg"); //Opicional

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

OBS: Esta biblioteca está em Beta 1. Portanto, alterações a nível de estrutura ainda podem acontecer. Caso deseje utiliza-la em um projeto já em produção, você deve ter em mente que pode chegar um momento de necessitar mudar sua implementação - ou não.  
