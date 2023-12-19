package models

class Admin (
    private var routerList: MutableList<Router>,
    private var cicleTime: Int
) {
    // getters and setters
    fun getRouterList(): MutableList<Router> {
        return routerList
    }

    fun setRouterList(routerList: MutableList<Router>) {
        this.routerList = routerList
    }

    fun getCicleTime(): Int {
        return cicleTime
    }

    fun setCicleTime(cicleTime: Int) {
        this.cicleTime = cicleTime
    }
}