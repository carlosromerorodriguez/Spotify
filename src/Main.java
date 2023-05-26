import business.*;
import persistance.*;
import presentation.controller.*;
import presentation.view.SignUpView;
import presentation.view.WelcomeView;
import presentation.controller.WelcomeController;
import presentation.view.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) {
        try {
            //TODO: Limpiar el fichero de informacion de usuario
            //clearTxtFile();

            // TODO: Esto SIEMPRE es igual, no hay que cambiarlo
            ConfigDatabaseDAO configDatabaseDAO = new ConfigDatabaseDAO("data/config.json");
            DDBBAccess ddBBAccess = new DDBBAccess(configDatabaseDAO);
            APIAccess api = new APIAccess();

            // TODO: Esto se puede modificar
            UserDatabaseDAO userDatabaseDAO = new UserDatabaseDAO(ddBBAccess);
            BusinessLogicUser businessLogicUser = new BusinessLogicUser(userDatabaseDAO);
            BusinessLogicMPlayer businessLogicMPlayer = new BusinessLogicMPlayer();
            SongDatabaseDAO songDatabaseDAO = new SongDatabaseDAO(ddBBAccess);
            StatisticsDatabaseDAO statisticsDatabaseDAO = new StatisticsDatabaseDAO(ddBBAccess);
            BusinessLogicSong businessLogicSong = new BusinessLogicSong(songDatabaseDAO, statisticsDatabaseDAO);
            BusinessLogicMusic businessLogicMusic = new BusinessLogicMusic(songDatabaseDAO, api);
            PlaylistDatabaseDAO playlistDatabaseDAO = new PlaylistDatabaseDAO(ddBBAccess);
            BusinessLogicPlayList businessLogicPlayList = new BusinessLogicPlayList(playlistDatabaseDAO, userDatabaseDAO);

            SignUpView signUpView = new SignUpView();
            LogOutView logOutView = new LogOutView();
            SignInView signInView = new SignInView();
            WelcomeView welcomeView = new WelcomeView();
            PlayMusicView playMusicView = new PlayMusicView();
            MainMenuView mainMenuView = new MainMenuView();
            AddMusicView addMusicView = new AddMusicView();
            DeleteSongFromPlaylistView deleteSongFromPlaylistView = new DeleteSongFromPlaylistView();
            AddPlaylistView addPlaylistView = new AddPlaylistView();
            DeletePlaylistView deletePlaylistView = new DeletePlaylistView();
            ListMusicView listMusicView = new ListMusicView();
            DeleteMusicView deleteMusicView = new DeleteMusicView();
            PlaylistView playlistView = new PlaylistView();
            PlaylistSongsView playlistSongsView = new PlaylistSongsView();
            MusicStatisticsView musicStatisticsView = new MusicStatisticsView();
            ShowMusicInfoView showMusicInfoView = new ShowMusicInfoView();
            AddSongToPlaylistView addSongToPlaylistView = new AddSongToPlaylistView();

            ViewsController viewsController = new ViewsController(signInView, signUpView, logOutView, welcomeView, addMusicView,
                    listMusicView, deleteMusicView, mainMenuView, playMusicView, playlistView, musicStatisticsView, showMusicInfoView,
                    addPlaylistView, playlistSongsView, addSongToPlaylistView, deletePlaylistView, deleteSongFromPlaylistView);

            //LogOutController logOutController = new LogOutController(businessLogicUser);
            SignUpController signUpController = new SignUpController(signUpView, businessLogicUser, viewsController);
            SignInController signInController = new SignInController(signInView, businessLogicUser, viewsController);
            WelcomeController welcomeController = new WelcomeController(welcomeView, businessLogicUser, viewsController);
            AddSongToPlaylistController addSongToPlaylistController = new AddSongToPlaylistController(businessLogicPlayList, viewsController, addSongToPlaylistView);
            PlayMusicController playMusicController = new PlayMusicController(playMusicView, businessLogicMPlayer, viewsController);
            DeleteMusicController deleteMusicController = new DeleteMusicController(deleteMusicView, businessLogicSong);
            MusicStatisticsController musicStatisticsController = new MusicStatisticsController(musicStatisticsView, businessLogicSong);
            ListMusicController listMusicController = new ListMusicController(businessLogicMusic, viewsController, listMusicView, showMusicInfoView);
            PlaylistSongsController playlistSongsController = new PlaylistSongsController(playlistSongsView, businessLogicPlayList, viewsController, addSongToPlaylistView, deleteSongFromPlaylistView);
            PlaylistController playlistController = new PlaylistController(playlistView, businessLogicPlayList, viewsController, playlistSongsController);
            AddPlaylistController addPlaylistController = new AddPlaylistController(businessLogicPlayList, viewsController, addPlaylistView, playlistController);
            DeletePlaylistController deletePlaylistController = new DeletePlaylistController(businessLogicPlayList, viewsController, deletePlaylistView, playlistController);
            DeleteSongFromPlaylistController deleteSongFromPlaylistController = new DeleteSongFromPlaylistController(businessLogicPlayList, viewsController, deleteSongFromPlaylistView, playlistSongsController);


            signUpView.registerController(signUpController);
            signUpView.backController(signUpController);
            signInView.logInController(signInController);
            signInView.backController(signInController);
            welcomeView.registerController(welcomeController);
            welcomeView.welcomeController(welcomeController);
            playMusicView.playMusicController(playMusicController);
            addPlaylistView.addPlaylistController(addPlaylistController);
            addPlaylistView.backPlaylistController(addPlaylistController);
            deletePlaylistView.deletePlaylistController(deletePlaylistController);
            deletePlaylistView.backPlaylistController(deletePlaylistController);

            AddMusicController addMusicController = new AddMusicController(businessLogicSong, viewsController, addMusicView, listMusicController);
            addMusicView.addMusicController(addMusicController);
            addMusicView.backSongController(addMusicController);
            deleteMusicView.deleteMusicController(deleteMusicController);

            MainMenuController mainMenuController = new MainMenuController(viewsController, listMusicController, musicStatisticsController, playlistController);
            mainMenuView.setActionListeners(mainMenuController);
            listMusicView.actionLinker(listMusicController);


            viewsController.createViewReproductor();
            //musicStatisticsView.MusicStatisticsView();
            //musicStatisticsView.BarChartExample();
            //viewsController.createViewAddSong();
            //viewsController.createViewListSong();
            //viewsController.createViewDeleteSong();
            //viewsController.create();
            //MusicStatisticsView musicStatisticsView = new MusicStatisticsView();
            //musicStatisticsView.BarChartExample();
        } catch (Exception e) {
            System.out.println("Error al iniciar la aplicacion");
        }
    }

    private static void clearTxtFile() {
        try {
            Files.write(Paths.get("data/user/userInfo"), new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e){
            System.out.println("Error al limpiar el fichero de informacion de usuario");
        }

    }
}
