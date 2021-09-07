package com.esab.core.util;

import java.util.Collection;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;

public class EUUtils {
    public static final String SITEUID = "siteUID";
    private static final String GCE_CONTENT_CATALOG = "gceContentCatalog";
    private static final String ESAB_CONTENT_CATALOG = "esabContentCatalog";

    private static final String GCE = "gce-";
    private static final String APAC = "apac-";
    private static final String APAC_AU = "apac-au";
    private static final String APAC_CN = "apac-cn";
    private static final String APAC_SEA = "apac-sea";
    private static final String SESSIONSERVICE = "sessionService";

    private EUUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static SessionService getSession() {
        return Registry.getApplicationContext().getBean(SESSIONSERVICE,
                SessionService.class);
    }

    public static CMSSiteService getCMSSiteBean() {
        return Registry.getApplicationContext().getBean("cmsSiteService",
                CMSSiteService.class);
    }

    public static CatalogVersionModel getSessioCatalogVersion() {
        final Collection<CatalogVersionModel> catalogVersionModel = Registry.getApplicationContext().getBean("catalogVersionService",
                CatalogVersionService.class).getSessionCatalogVersions();
        CatalogVersionModel result = null;
        for (final CatalogVersionModel cVersion : catalogVersionModel) {
            if (GCE_CONTENT_CATALOG.equalsIgnoreCase(cVersion.getCatalog().getId())) {
                result = cVersion;
            }
            if (ESAB_CONTENT_CATALOG.equalsIgnoreCase(cVersion.getCatalog().getId())) {
                result = cVersion;
            }
        }
        return result;
    }

    public static ConfigurationService getConfigurationService() {
        return Registry.getApplicationContext().getBean("configurationService",
                ConfigurationService.class);
    }

    public static String getSiteUID() {

        CMSSiteService cmsSiteService = getCMSSiteBean();
        CMSSiteModel cmsSitemodel = cmsSiteService.getCurrentSite();

        if (cmsSitemodel != null) {
            return cmsSitemodel.getUid();
        } else {
            return EUUtils.getSiteUIDFromSession();
        }
    }

    private static String getSiteUIDFromSession() {
        Object siteUID = Registry.getApplicationContext().getBean(SESSIONSERVICE,
                SessionService.class).getCurrentSession().getAttribute(SITEUID);
        return (String) siteUID;
    }

    public static boolean isGCEWebsite() {
        String siteUID = getSiteUID();
        return siteUID != null && siteUID.startsWith(GCE);
    }

    public static boolean isAPACWebsite() {
        String siteUID = getSiteUID();
        return siteUID != null && siteUID.startsWith(APAC);
    }

    public static boolean isAPACAUWebsite() {
        String siteUID = getSiteUID();
        return siteUID != null && siteUID.startsWith(APAC_AU);
    }

    public static boolean isAPACCNWebsite() {
        String siteUID = getSiteUID();
        return siteUID != null && siteUID.startsWith(APAC_CN);
    }

    public static boolean isAPACSEAWebsite() {
        String siteUID = getSiteUID();
        return siteUID != null && siteUID.startsWith(APAC_SEA);
    }
}

