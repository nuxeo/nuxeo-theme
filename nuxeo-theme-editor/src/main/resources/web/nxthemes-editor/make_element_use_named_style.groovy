import org.nuxeo.theme.Manager
import org.nuxeo.theme.editor.Events
import org.nuxeo.theme.events.EventContext
import org.nuxeo.theme.events.EventManager
import org.nuxeo.theme.elements.Element
import org.nuxeo.theme.elements.ElementFormatter
import org.nuxeo.theme.events.EventContext
import org.nuxeo.theme.formats.styles.Style
import org.nuxeo.theme.themes.ThemeManager
import org.nuxeo.theme.formats.FormatType
import org.nuxeo.theme.types.TypeFamily
import org.nuxeo.theme.formats.styles.Style

id = Request.getParameter("id")
inheritedName = Request.getParameter("style_name")
themeName = Request.getParameter("theme_name")

Element element = ThemeManager.getElementById(id);
FormatType styleType = (FormatType) Manager.getTypeRegistry().lookup(TypeFamily.FORMAT, "style");
Style style = (Style) ElementFormatter.getFormatByType(element, styleType);

ThemeManager themeManager = Manager.getThemeManager()

// Make the style no longer inherits from other another style if 'inheritedName' is null
if (inheritedName == null) {
    ThemeManager.removeInheritanceTowards(style);
} else {
    themeId = themeName.split("/")[0];
    Style inheritedStyle = (Style) themeManager.getNamedObject(themeId, "style", inheritedName);
    if (inheritedStyle != null) {
        themeManager.makeFormatInherit(style, inheritedStyle);
    }
}

EventManager eventManager = Manager.getEventManager()
eventManager.notify(Events.THEME_MODIFIED_EVENT, new EventContext(element, null));
eventManager.notify(Events.STYLES_MODIFIED_EVENT, new EventContext(element, null));
