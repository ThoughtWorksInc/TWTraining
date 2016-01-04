using System.Web;
using Ninject.Activation;
using Ninject.Syntax;

namespace VideoWorld.Configuration
{

    //See: http://iridescence.no/post/Session-Scoped-Bindings-With-Ninject-2.aspx
    public static class NinjectSessionScopingExtension
    {
        private const string SessionKey = "Ninject Session Scope Sync Root";

        public static void InSessionScope<T>(this IBindingInSyntax<T> parent)
        {
            parent.InScope(SessionScopeCallback);
        }

        private static object SessionScopeCallback(IContext context)
        {
            if (HttpContext.Current.Session[SessionKey] == null)
            {
                HttpContext.Current.Session[SessionKey] = new object();
            }

            return HttpContext.Current.Session[SessionKey];
        }
    }
}