Esse é o backend. para front, vá para https://github.com/pedroandriottii/hunt-hub-front
This is the backend. for the front ent, visit https://github.com/pedroandriottii/hunt-hub-front

# Hunt-hub 🇧🇷
Repositório destinado ao projeto Hunt-hub, da disciplina de Requisitos, projeto de software e validação, do 5° período no CESAR School.

## Descrição do domínio
Hunt Hub é uma plataforma destinada a conectar hunter com POs que buscam tais profissionais para realizar projetos. 
O PO anuncia uma task, faz um pagamento prévio, adicionada também à uma taxa cobrada pela plataforma ajustada ao valor da task. 
Ademais, o PO fornece uma descrição detalhada do serviço, incluindo tags que indicam o tipo de profissional necessário 
(como desenvolvedor back-end, administrador de banco de dados, cientista de dados, entre outros), a quantidade de reuniões desejadas com o Hunter, 
o pagamento, e por fim, o prazo de entrega esperado.  O sistema também inclui um mecanismo de qualificação para os hunters e POs, 
baseado em níveis (“levels”) e troféus (“achievements”), que recompensam os profissionais conforme completam novas tasks.<br>
Para uma descrição mais aprofundada, acesse nossa
<a href="https://docs.google.com/document/d/1wTOTiOhm-b9dQ1bshlAFMc6ix6zCXaaZUpEvAewrogo/edit?usp=sharing" target="_blank">Descrição de Domínio</a>

## Padrões de Projeto utilizados
No desenvolvimento do nosso projeto, utilizamos os seguintes padrões de projetos:
- <strong>Observer:</strong> O Observer é usado no gerenciamento de notificações entre usuários (POs e Hunters). Ele promove o desacoplamento entre os serviços que geram eventos e os usuários que precisam ser notificados, permitindo uma comunicação eficiente e flexível. Isso facilita a manutenção e a expansão do sistema, permitindo adicionar novos tipos de notificações sem alterar o código existente. O uso do Builder pode ser encontrado nas pastas hunt-hub/bdd/src/test/java/com/groupseven/hunthub/steps/builder e hunt-hub/bdd/src/test/java/com/groupseven/hunthub/steps/director
- <strong>Builder:</strong> O Builder é usado para criar objetos complexos, como Hunter, PO e Task, de maneira organizada e flexível. Ele permite construir objetos passo a passo, garantindo consistência e melhorando a legibilidade do código. Esse padrão facilita a criação de instâncias completas, mesmo para objetos com muitos atributos ou configurações opcionais. O uso do observer pode ser encontrado no arquivo NotificationService, localizado na pasta hunt-hub/domain/src/main/java/com/groupseven/hunthub/domain/services

## Como Rodar:

<table>
  <tr>- Clone o repositório do Backend em uma pasta:
  <dt>

      git clone https://github.com/CarlosAugustoP/hunt-hub.git
  </dt>
  
  <tr>- Clone o repositório do Frontend em uma pasta:
  <dt>

      git clone https://github.com/pedroandriottii/hunt-hub-front.git
  </dt>

  <tr>- Dentro de um terminal do Backend, rode:
  <dt>

      cd ./hunt-hub
      docker compose up
  </dt>

  Obs: Certifique-se que não haja conflito de volumes ou contêineres. O nosso banco PostgreSQL roda na porta 5432.

  <tr>- Dentro de outro terminal do Backend, rode:
  <dt>

      cd ./hunt-hub
      mvn clean install
  </dt>

  <tr>- Dentro de outro terminal do Backend, rode:
  <dt>

      cd ./hunt-hub/application
      mvn spring-boot:run
  </dt>

  <tr>- Dentro de um terminal do Frontend, rode:
  <dt>

      cd ./hunt-hub-front
      npm install
      npm run build
      npm run start
  </dt>
  <tr>- Caso haja algum problema inesperado. use o comando seguinte:
  <dt>

    npm run dev 
  </dt>
  
</table>

## Links do projeto
<a href="https://www.figma.com/design/DPN9DklMVoONeVdGGPau6c/Prot%C3%B3tipo-de-Baixa-HuntHub?node-id=0-1&t=yah2qWUIoqgVMocu-1" target="_blank">Protótipo</a><br>
<a href="https://drive.google.com/file/d/1TEKOA83vKyH-D6SFzGGHJvHdO2vmIzfg/view?usp=sharing" target="_blank">Mapa de histórias</a><br>
<a href="https://drive.google.com/drive/folders/1tDZWO5S-25sfzKvNG2PZSxEAYW9F2rRk?usp=sharing" target="_blank">Context Map</a><br>
<a href="https://drive.google.com/drive/folders/197pChlxycG2MBROmAXLyGv3-AZJj0snI?usp=sharing" target="_blank">Drive</a><br>

## Nossos desenvolvedores
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/paixaoao">
        <img src="https://avatars.githubusercontent.com/u/126728380?v=4" width="100px;" alt="Foto Paixão"/><br>
        <sub>
          <b>Arthur Paixão</b>
        </sub>
      </a>
      <br>
      <sub>aptm@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/CarlosAugustoP">
        <img src="https://avatars.githubusercontent.com/u/117591564?v=4" width="100px;" alt="Foto Carlos"/><br>
        <sub>
          <b>Carlos Augusto</b>
        </sub>
      </a>
      <br>
      <sub>capvf@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/EstelaLacerda">
        <img src="https://avatars.githubusercontent.com/u/117921412?v=4" width="100px;" alt="Foto Estela"/><br>
        <sub>
          <b>Estela Lacerda</b>
        </sub>
      </a>
      <br>
      <sub>elo@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/gabrielpires-1">
        <img src="https://avatars.githubusercontent.com/u/111147078?v=4" width="100px;" alt="Foto Pires"/><br>
        <sub>
          <b>Gabriel Pires</b>
        </sub>
      </a>
      <br>
      <sub>gpac@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/grossiter04">
        <img src="https://avatars.githubusercontent.com/u/116268469?v=4" width="100px;" alt="Foto Rossiter"/><br>
        <sub>
          <b>Gabriel Rossiter</b>
        </sub>
      </a>      
      <br>
      <sub>gsr@cesar.school</sub>
    </td>
  </tr>
</table>
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/luismingati">
        <img src="https://avatars.githubusercontent.com/u/71787770?v=4" width="100px;" alt="Foto Luis"/><br>
        <sub>
          <b>Luis Otávio</b>
        </sub>
      </a>
      <br>
      <sub>locm@cesar.school</sub>
    </td>
  <td align="center">
      <a href="https://github.com/MatheusGom">
        <img src="https://avatars.githubusercontent.com/u/117746778?v=4" width="100px;" alt="Foto Matheus"/><br>
        <sub>
          <b>Matheus Gomes</b>
        </sub>
      </a>
      <br>
      <sub>mga@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/paulo-campos-57">
        <img src="https://avatars.githubusercontent.com/u/77108503?v=4" width="100px;" alt="Foto Paulo"/><br>
        <sub>
          <b>Paulo Campos</b>
        </sub>
      </a>
      <br>
      <sub>pmc3@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/pedroandriottii">
        <img src="https://avatars.githubusercontent.com/u/112347899?v=4" width="100px;" alt="Foto Pedro"/><br>
        <sub>
          <b>Pedro Andriotti</b>
        </sub>
      </a>
      <br>
      <sub>phab@cesar.school</sub>
    </td>
  </tr>
</table>
<br>

# Hunt-hub 🇺🇸
Repository intended for the Hunt-hub project, part of the Requirements, Software Project, and Validation course in the 5th semester at CESAR School.

## Domain description
Hunt Hub is a platform designed to connect hunters with POs looking for professionals to carry out projects. The PO posts a task, makes a preliminary payment, which includes a fee charged by the platform based on the task's value. Additionally, the PO provides a detailed description of the service, including tags that indicate the type of professional needed (such as back-end developer, database administrator, data scientist, among others), the desired number of meetings with the hunter, payment details, and the expected delivery deadline. The system also includes a qualification mechanism for both hunters and POs, based on levels and achievements, rewarding professionals as they complete new tasks.  
For a more in-depth description, please visit our  
<a href="https://docs.google.com/document/d/1wTOTiOhm-b9dQ1bshlAFMc6ix6zCXaaZUpEvAewrogo/edit?usp=sharing" target="_blank">document.</a>

## Design Patterns Used
In the development of our project, we utilized the following design patterns:
- <strong>Observer:</strong> The Observer pattern is used for managing notifications between users (POs and Hunters). It promotes decoupling between the services that generate events and the users who need to be notified, enabling efficient and flexible communication. This simplifies system maintenance and expansion, allowing new types of notifications to be added without altering the existing code. The usage of the Observer pattern can be found in the NotificationService file, located in the hunt-hub/domain/src/main/java/com/groupseven/hunthub/domain/services folder.
- <strong>Builder:</strong> The Builder pattern is used to create complex objects, such as Hunter, PO, and Task, in an organized and flexible manner. It allows building objects step by step, ensuring consistency and improving code readability. This pattern facilitates the creation of complete instances, even for objects with many attributes or optional configurations. The usage of the Builder pattern can be found in the folders hunt-hub/bdd/src/test/java/com/groupseven/hunthub/steps/builder and hunt-hub/bdd/src/test/java/com/groupseven/hunthub/steps/director.

## How to Run:

<table>
  <tr>- Clone the Backend repository into a folder:
  <dt>

      git clone https://github.com/CarlosAugustoP/hunt-hub.git
  </dt>
  
  <tr>- Clone the Frontend repository into a folder:
  <dt>

      git clone https://github.com/pedroandriottii/hunt-hub-front.git
  </dt>

  <tr>- In a terminal for the Backend, run:
  <dt>

      cd ./hunt-hub
      docker compose up
  </dt>

  <tr>- In another terminal for the Backend, run:
  <dt>

      cd ./hunt-hub
      mvn clean install
  </dt>

  <tr>- In another terminal for the Backend, run:
  <dt>

      cd ./hunt-hub/application
      mvn spring-boot:run
  </dt>

  <tr>- In a terminal for the Frontend, run:
  <dt>

      cd ./hunt-hub-front
      npm install
      npm run build
      npm run start
  </dt>
  
</table>

## Project links
<a href="https://www.figma.com/design/DPN9DklMVoONeVdGGPau6c/Prot%C3%B3tipo-de-Baixa-HuntHub?node-id=0-1&t=yah2qWUIoqgVMocu-1" target="_blank">Prototype</a><br>
<a href="https://drive.google.com/file/d/1TEKOA83vKyH-D6SFzGGHJvHdO2vmIzfg/view?usp=sharing" target="_blank">History Map</a><br>
<a href="https://drive.google.com/drive/folders/1tDZWO5S-25sfzKvNG2PZSxEAYW9F2rRk?usp=sharing" target="_blank">Context Map</a><br>
<a href="https://drive.google.com/drive/folders/197pChlxycG2MBROmAXLyGv3-AZJj0snI?usp=sharing" target="_blank">Drive</a><br>

## Our developers
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/paixaoao">
        <img src="https://avatars.githubusercontent.com/u/126728380?v=4" width="100px;" alt="Foto Paixão"/><br>
        <sub>
          <b>Arthur Paixão</b>
        </sub>
      </a>
      <br>
      <sub>aptm@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/CarlosAugustoP">
        <img src="https://avatars.githubusercontent.com/u/117591564?v=4" width="100px;" alt="Foto Carlos"/><br>
        <sub>
          <b>Carlos Augusto</b>
        </sub>
      </a>
      <br>
      <sub>capvf@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/EstelaLacerda">
        <img src="https://avatars.githubusercontent.com/u/117921412?v=4" width="100px;" alt="Foto Estela"/><br>
        <sub>
          <b>Estela Lacerda</b>
        </sub>
      </a>
      <br>
      <sub>elo@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/gabrielpires-1">
        <img src="https://avatars.githubusercontent.com/u/111147078?v=4" width="100px;" alt="Foto Pires"/><br>
        <sub>
          <b>Gabriel Pires</b>
        </sub>
      </a>
      <br>
      <sub>gpac@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/grossiter04">
        <img src="https://avatars.githubusercontent.com/u/116268469?v=4" width="100px;" alt="Foto Rossiter"/><br>
        <sub>
          <b>Gabriel Rossiter</b>
        </sub>
      </a>      
      <br>
      <sub>gsr@cesar.school</sub>
    </td>
  </tr>
</table>
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/luismingati">
        <img src="https://avatars.githubusercontent.com/u/71787770?v=4" width="100px;" alt="Foto Luis"/><br>
        <sub>
          <b>Luis Otávio</b>
        </sub>
      </a>
      <br>
      <sub>locm@cesar.school</sub>
    </td>
  <td align="center">
      <a href="https://github.com/MatheusGom">
        <img src="https://avatars.githubusercontent.com/u/117746778?v=4" width="100px;" alt="Foto Matheus"/><br>
        <sub>
          <b>Matheus Gomes</b>
        </sub>
      </a>
      <br>
      <sub>mga@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/paulo-campos-57">
        <img src="https://avatars.githubusercontent.com/u/77108503?v=4" width="100px;" alt="Foto Paulo"/><br>
        <sub>
          <b>Paulo Campos</b>
        </sub>
      </a>
      <br>
      <sub>pmc3@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/pedroandriottii">
        <img src="https://avatars.githubusercontent.com/u/112347899?v=4" width="100px;" alt="Foto Pedro"/><br>
        <sub>
          <b>Pedro Andriotti</b>
        </sub>
      </a>
      <br>
      <sub>phab@cesar.school</sub>
    </td>
  </tr>
</table>
