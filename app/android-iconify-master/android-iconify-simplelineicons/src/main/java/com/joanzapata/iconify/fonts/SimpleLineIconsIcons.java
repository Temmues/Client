package com.joanzapata.iconify.fonts;

import com.joanzapata.iconify.Icon;

public enum SimpleLineIconsIcons implements Icon {
    icon_user_female('\ue000'),
    icon_user_follow('\ue002'),
    icon_user_following('\ue003'),
    icon_user_unfollow('\ue004'),
    icon_trophy('\ue006'),
    icon_screen_smartphone('\ue010'),
    icon_screen_desktop('\ue011'),
    icon_plane('\ue012'),
    icon_notebook('\ue013'),
    icon_moustache('\ue014'),
    icon_mouse('\ue015'),
    icon_magnet('\ue016'),
    icon_energy('\ue020'),
    icon_emoticon_smile('\ue021'),
    icon_disc('\ue022'),
    icon_cursor_move('\ue023'),
    icon_crop('\ue024'),
    icon_credit_card('\ue025'),
    icon_chemistry('\ue026'),
    icon_user('\ue005'),
    icon_speedometer('\ue007'),
    icon_social_youtube('\ue008'),
    icon_social_twitter('\ue009'),
    icon_social_tumblr('\ue00a'),
    icon_social_facebook('\ue00b'),
    icon_social_dropbox('\ue00c'),
    icon_social_dribbble('\ue00d'),
    icon_shield('\ue00e'),
    icon_screen_tablet('\ue00f'),
    icon_magic_wand('\ue017'),
    icon_hourglass('\ue018'),
    icon_graduation('\ue019'),
    icon_ghost('\ue01a'),
    icon_game_controller('\ue01b'),
    icon_fire('\ue01c'),
    icon_eyeglasses('\ue01d'),
    icon_envelope_open('\ue01e'),
    icon_envelope_letter('\ue01f'),
    icon_bell('\ue027'),
    icon_badge('\ue028'),
    icon_anchor('\ue029'),
    icon_wallet('\ue02a'),
    icon_vector('\ue02b'),
    icon_speech('\ue02c'),
    icon_puzzle('\ue02d'),
    icon_printer('\ue02e'),
    icon_present('\ue02f'),
    icon_playlist('\ue030'),
    icon_pin('\ue031'),
    icon_picture('\ue032'),
    icon_map('\ue033'),
    icon_layers('\ue034'),
    icon_handbag('\ue035'),
    icon_globe_alt('\ue036'),
    icon_globe('\ue037'),
    icon_frame('\ue038'),
    icon_folder_alt('\ue039'),
    icon_film('\ue03a'),
    icon_feed('\ue03b'),
    icon_earphones_alt('\ue03c'),
    icon_earphones('\ue03d'),
    icon_drop('\ue03e'),
    icon_drawer('\ue03f'),
    icon_docs('\ue040'),
    icon_directions('\ue041'),
    icon_direction('\ue042'),
    icon_diamond('\ue043'),
    icon_cup('\ue044'),
    icon_compass('\ue045'),
    icon_call_out('\ue046'),
    icon_call_in('\ue047'),
    icon_call_end('\ue048'),
    icon_calculator('\ue049'),
    icon_bubbles('\ue04a'),
    icon_briefcase('\ue04b'),
    icon_book_open('\ue04c'),
    icon_basket_loaded('\ue04d'),
    icon_basket('\ue04e'),
    icon_bag('\ue04f'),
    icon_action_undo('\ue050'),
    icon_action_redo('\ue051'),
    icon_wrench('\ue052'),
    icon_umbrella('\ue053'),
    icon_trash('\ue054'),
    icon_tag('\ue055'),
    icon_support('\ue056'),
    icon_size_fullscreen('\ue057'),
    icon_size_actual('\ue058'),
    icon_shuffle('\ue059'),
    icon_share_alt('\ue05a'),
    icon_share('\ue05b'),
    icon_rocket('\ue05c'),
    icon_question('\ue05d'),
    icon_pie_chart('\ue05e'),
    icon_pencil('\ue05f'),
    icon_note('\ue060'),
    icon_music_tone_alt('\ue061'),
    icon_music_tone('\ue062'),
    icon_microphone('\ue063'),
    icon_loop('\ue064'),
    icon_logout('\ue065'),
    icon_login('\ue066'),
    icon_list('\ue067'),
    icon_like('\ue068'),
    icon_home('\ue069'),
    icon_grid('\ue06a'),
    icon_graph('\ue06b'),
    icon_equalizer('\ue06c'),
    icon_dislike('\ue06d'),
    icon_cursor('\ue06e'),
    icon_control_start('\ue06f'),
    icon_control_rewind('\ue070'),
    icon_control_play('\ue071'),
    icon_control_pause('\ue072'),
    icon_control_forward('\ue073'),
    icon_control_end('\ue074'),
    icon_calendar('\ue075'),
    icon_bulb('\ue076'),
    icon_bar_chart('\ue077'),
    icon_arrow_up('\ue078'),
    icon_arrow_right('\ue079'),
    icon_arrow_left('\ue07a'),
    icon_arrow_down('\ue07b'),
    icon_ban('\ue07c'),
    icon_bubble('\ue07d'),
    icon_camcorder('\ue07e'),
    icon_camera('\ue07f'),
    icon_check('\ue080'),
    icon_clock('\ue081'),
    icon_close('\ue082'),
    icon_cloud_download('\ue083'),
    icon_cloud_upload('\ue084'),
    icon_doc('\ue085'),
    icon_envelope('\ue086'),
    icon_eye('\ue087'),
    icon_flag('\ue088'),
    icon_folder('\ue089'),
    icon_heart('\ue08a'),
    icon_info('\ue08b'),
    icon_key('\ue08c'),
    icon_link('\ue08d'),
    icon_lock('\ue08e'),
    icon_lock_open('\ue08f'),
    icon_magnifier('\ue090'),
    icon_magnifier_add('\ue091'),
    icon_magnifier_remove('\ue092'),
    icon_paper_clip('\ue093'),
    icon_paper_plane('\ue094'),
    icon_plus('\ue095'),
    icon_pointer('\ue096'),
    icon_power('\ue097'),
    icon_refresh('\ue098'),
    icon_reload('\ue099'),
    icon_settings('\ue09a'),
    icon_star('\ue09b'),
    icon_symbol_female('\ue09c'),
    icon_symbol_male('\ue09d'),
    icon_target('\ue09e'),
    icon_volume_1('\ue09f'),
    icon_volume_2('\ue0a0'),
    icon_volume_off('\ue0a1'),
    icon_users('\ue001');

    char character;

    SimpleLineIconsIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
