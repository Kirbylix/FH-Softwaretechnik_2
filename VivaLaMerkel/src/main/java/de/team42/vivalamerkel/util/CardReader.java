package de.team42.vivalamerkel.util;

import de.team42.vivalamerkel.util.enums.CardType;
import de.team42.vivalamerkel.util.enums.GamePhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CardReader {
    private static final Logger LOG = LogManager.getLogger(CardReader.class.getName());

    private static final String BUNDLE_NAME = "cards";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(CardReader.BUNDLE_NAME);

    private CardReader(){}

    public static boolean getBoolean(final String key)
    {
        try {
            return Boolean.parseBoolean(CardReader.RESOURCE_BUNDLE.getString(key));
        } catch (final Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return false;
    }

    public static int getInt(final String key)
    {
        try {
            return Integer.parseInt(CardReader.RESOURCE_BUNDLE.getString(key));
        } catch (final Exception e) {
            LOG.info(e.getMessage());
        }
        return 9999;
    }

    public static String getString(final String key)
    {
        try {
            return CardReader.RESOURCE_BUNDLE.getString(key);
        } catch (final MissingResourceException e) {
            LOG.info(e.getMessage());
        }
        return "";
    }

    public static CardType getCardType(final String key)
    {
        try {
            return CardType.getCardType(CardReader.RESOURCE_BUNDLE.getString(key));
        } catch (final MissingResourceException e) {
            LOG.info(e.getMessage());
        }
        return null;
    }

    public static GamePhase getPhase(final String key)
    {
        try {
            return GamePhase.getPhase(CardReader.RESOURCE_BUNDLE.getString(key));
        } catch (final MissingResourceException e) {
            LOG.info(e.getMessage());
        }
        return null;
    }

}