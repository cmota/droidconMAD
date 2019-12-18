import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        
        UINavigationBar.appearance().isTranslucent = false
        UITabBar.appearance().tintColor = themeBaseColor
        
        if let font = UIFont.init(name: "OpenSans-Semibold", size: 15) {
            UIBarButtonItem.appearance().setTitleTextAttributes([NSAttributedStringKey.foregroundColor: UIColor.white, NSAttributedStringKey.font: font], for: .normal)
        }
        
        let frame = UIScreen.main.bounds
        window = UIWindow(frame: frame)
        
        let tabBarController = UITabBarController()
        var titleTextAttributes = [NSAttributedString.Key : Any]()
        
        let favoritesViewController = FavoritesViewController()
        let schedulesViewController = SchedulesViewController()
        let speakersViewController = SpeakersViewController()
        let venueViewController = VenueViewController()
        
        schedulesViewController.sessionDataHandler = favoritesViewController
        
        let schedulesNavigationController = UINavigationController.init(rootViewController: schedulesViewController)
        let favoritesNavigationController = UINavigationController.init(rootViewController: favoritesViewController)
        let speakersNavigationController = UINavigationController.init(rootViewController: speakersViewController)
        
        if let font = UIFont.init(name: "OpenSans-Semibold", size: 17) {
            titleTextAttributes = [NSAttributedStringKey.foregroundColor: UIColor.white, NSAttributedStringKey.font: font]
        }
        else {
            titleTextAttributes = [NSAttributedStringKey.foregroundColor: UIColor.white]
        }
        
        schedulesNavigationController.navigationBar.tintColor = .white
        schedulesNavigationController.navigationBar.barTintColor = themeBaseColor
        schedulesNavigationController.navigationBar.titleTextAttributes = titleTextAttributes
        
        favoritesNavigationController.navigationBar.tintColor = .white
        favoritesNavigationController.navigationBar.barTintColor = themeBaseColor
        favoritesNavigationController.navigationBar.titleTextAttributes = titleTextAttributes
        
        speakersNavigationController.navigationBar.tintColor = .white
        speakersNavigationController.navigationBar.barTintColor = themeBaseColor
        speakersNavigationController.navigationBar.titleTextAttributes = titleTextAttributes
        
        tabBarController.viewControllers = [schedulesNavigationController, favoritesNavigationController, speakersNavigationController, venueViewController]
        
        if let schedulesTab = tabBarController.tabBar.items?[0] {
            schedulesTab.image = UIImage.init(named: "clock")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate)
            schedulesTab.title = "Schedule"
        }
        
        if let schedulesTab = tabBarController.tabBar.items?[1] {
            schedulesTab.image = UIImage.init(named: "favorite")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate)
            schedulesTab.title = "Favorites"
        }
        
        if let speakersTab = tabBarController.tabBar.items?[2] {
            speakersTab.image = UIImage.init(named: "lecture")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate)
            speakersTab.title = "Speakers"
        }
        
        if let speakersTab = tabBarController.tabBar.items?[3] {
            speakersTab.image = UIImage.init(named: "about")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate)
            speakersTab.title = "About"
        }
        
        if let window = window {
            window.rootViewController = tabBarController
            window.makeKeyAndVisible()
        }
        
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {}

    func applicationDidEnterBackground(_ application: UIApplication) {}

    func applicationWillEnterForeground(_ application: UIApplication) {}

    func applicationDidBecomeActive(_ application: UIApplication) {}

    func applicationWillTerminate(_ application: UIApplication) {}
}
