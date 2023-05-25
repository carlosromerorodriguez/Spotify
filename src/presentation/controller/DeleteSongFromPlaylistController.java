package presentation.controller;

import business.BusinessLogicPlayList;
import business.exceptions.TitleException;
import presentation.view.DeleteSongFromPlaylistView;
import presentation.view.PlaylistSongsView;
import presentation.view.ViewsController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteSongFromPlaylistController implements ActionListener {
    private final BusinessLogicPlayList businessLogicPlaylist;
    private final ViewsController viewsController;
    private final DeleteSongFromPlaylistView deleteSongFromPlaylistView;
    private final PlaylistSongsController playlistController;

    public DeleteSongFromPlaylistController(BusinessLogicPlayList businessLogicPlaylist, ViewsController viewsController, DeleteSongFromPlaylistView deleteSongFromPlaylistView, PlaylistSongsController playlistSongsController) {
        this.businessLogicPlaylist = businessLogicPlaylist;
        this.viewsController = viewsController;
        this.deleteSongFromPlaylistView = deleteSongFromPlaylistView;
        this.playlistController = playlistSongsController;
        deleteSongFromPlaylistView.setListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(DeleteSongFromPlaylistView.DELETE_COMMAND)) {
            if (businessLogicPlaylist.deleteSongFromPlaylist(deleteSongFromPlaylistView.getPlaylistName(), deleteSongFromPlaylistView.getTitle())) {
                deleteSongFromPlaylistView.successfulDeleteSong();
            } else {
                deleteSongFromPlaylistView.notExistingSongError();
            }
            viewsController.setPlaylistSongsView();
            playlistController.loadSongsFromApi(deleteSongFromPlaylistView.getPlaylistName());

        }
        if (e.getActionCommand().equals(DeleteSongFromPlaylistView.BACK_FROM_DELETE)){
            viewsController.setPlaylistSongsView();
        }
    }
}