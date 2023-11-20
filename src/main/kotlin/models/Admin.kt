package models

class Admin (
    private var routerList: List<Router>,
    private var cicleTime: Int
) {
    // getters and setters
    fun getRouterList(): List<Router> {
        return routerList
    }

    fun setRouterList(routerList: List<Router>) {
        this.routerList = routerList
    }

    fun getCicleTime(): Int {
        return cicleTime
    }

    fun setCicleTime(cicleTime: Int) {
        this.cicleTime = cicleTime
    }
}