package models

class Page (
    private var pageId: Int = 0,
    private var pageContent: String = "",
    private var size: Int = 0,
    private var destinationIP: IP = IP(0,0),
    private var originIP: IP = IP(0,0)
){
    // getters and setters
    fun getPageId(): Int {
        return pageId
    }

    fun setPageId(pageId: Int) {
        this.pageId = pageId
    }

    fun getPageContent(): String {
        return pageContent
    }

    fun setPageName(pageName: String) {
        this.pageContent = pageName
    }

    fun getSize(): Int {
        return size
    }

    fun setSize(size: Int) {
        this.size = size
    }

    fun getDestinationRouterId(): Int {
        return destinationIP.getRouterID()
    }

    fun setDestinationRouterId(destinationId: Int) {
        this.destinationIP.setRouterID(destinationId)
    }

    fun getOriginRouterId(): Int {
        return originIP.getRouterID()
    }

    fun setOriginRouterId(originId: Int) {
        this.originIP.setRouterID(originId)
    }

    fun getOriginIP(): IP{
        return originIP
    }

    fun setOriginIP(ip:IP){
        this.originIP = ip
    }

    fun getDestinationIP(): IP{
        return destinationIP
    }

    fun setDestinationIP(ip:IP){
        this.destinationIP = ip
    }
}