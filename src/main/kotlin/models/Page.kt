package models

class Page (
    private var pageId: Int = 0,
    private var pageName: String = "",
    private var size: Int = 0,
    private var destinationRouterId: Int = 0,
    private var originRouterId: Int = 0,
){
    // getters and setters
    fun getPageId(): Int {
        return pageId
    }

    fun setPageId(pageId: Int) {
        this.pageId = pageId
    }

    fun getPageName(): String {
        return pageName
    }

    fun setPageName(pageName: String) {
        this.pageName = pageName
    }

    fun getSize(): Int {
        return size
    }

    fun setSize(size: Int) {
        this.size = size
    }

    fun getDestinationRouterId(): Int {
        return destinationRouterId
    }

    fun setDestinationRouterId(destinationRouterId: Int) {
        this.destinationRouterId = destinationRouterId
    }

    fun getOriginRouterId(): Int {
        return originRouterId
    }

    fun setOriginRouterId(originRouterId: Int) {
        this.originRouterId = originRouterId
    }
}