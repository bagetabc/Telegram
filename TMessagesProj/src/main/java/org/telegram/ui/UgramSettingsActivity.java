package org.telegram.ui;

import android.view.View;

import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalFragment;
import org.telegram.ui.Components.UniversalAdapter;

import java.util.ArrayList;

/** UGRAM-specific entry point for account identifiers and supported Telegram settings. */
public class UgramSettingsActivity extends UniversalFragment {
    private static final int ID_THEME = 1;
    private static final int ID_PRIVACY = 2;
    private static final int ID_PROXY = 3;
    private static final int ID_DELETED_INFO = 4;

    @Override
    protected boolean useSimpleActionBar() {
        return true;
    }

    @Override
    protected CharSequence getTitle() {
        return "UGRAM";
    }

    @Override
    protected void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader("Аккаунты"));
        boolean hasAccounts = false;
        for (int account = 0; account < UserConfig.MAX_ACCOUNT_COUNT; account++) {
            UserConfig config = UserConfig.getInstance(account);
            if (config.isClientActivated()) {
                hasAccounts = true;
                long userId = config.getClientUserId();
                items.add(UItem.asButton(100 + account,
                        "Аккаунт " + (account + 1) + "  ·  ID: " + userId));
            }
        }
        if (!hasAccounts) {
            items.add(UItem.asButton(199, "Нет активных аккаунтов"));
        }
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader("Оформление"));
        items.add(UItem.asButton(ID_THEME, "Темы и кастомизация"));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader("Приватность"));
        items.add(UItem.asButton(ID_PRIVACY, "Режим призрака и приватность"));
        items.add(UItem.asButton(ID_DELETED_INFO, "Удалённые сообщения: ограничения"));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader("Соединение"));
        items.add(UItem.asButton(ID_PROXY, "Настройки MTProto-прокси"));
        items.add(UItem.asShadow(null));
    }

    @Override
    protected void onClick(UItem item, View view, int position, float x, float y) {
        if (item.id >= 100 && item.id < 100 + UserConfig.MAX_ACCOUNT_COUNT) {
            int account = item.id - 100;
            if (LaunchActivity.instance != null && UserConfig.isValidAccount(account)) {
                LaunchActivity.instance.switchToAccount(account, true);
            }
            return;
        }
        switch (item.id) {
            case ID_THEME:
                presentFragment(new ThemeActivity(ThemeActivity.THEME_TYPE_BASIC));
                break;
            case ID_PRIVACY:
                presentFragment(new PrivacySettingsActivity());
                break;
            case ID_PROXY:
                presentFragment(new ProxySettingsActivity());
                break;
            case ID_DELETED_INFO:
                org.telegram.ui.ActionBar.AlertDialog.Builder builder =
                        new org.telegram.ui.ActionBar.AlertDialog.Builder(getContext());
                builder.setTitle("Удалённые сообщения");
                builder.setMessage("UGRAM сможет показывать только те сообщения, которые уже были сохранены локально на устройстве до удаления. Если Telegram удалил сообщение с сервера и оно не попало в локальный кэш или уведомление, восстановить его нельзя.");
                builder.setPositiveButton("Понятно", null);
                showDialog(builder.create());
                break;
        }
    }
}
