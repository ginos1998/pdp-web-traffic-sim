package models

class Package( // constructor
    private var packageId: Int,
    private var packageName: String,
    private var pageId: Int,
    private var totalPackages: Int,
    private var destinationIP: IP,
    private var actualIP: IP,
    private var nextIP: IP,
)
{
    // getters and setters
    fun getPackageId(): Int {
        return packageId
    }

    fun setPackageId(packageId: Int) {
        this.packageId = packageId
    }

    fun getPackageName(): String {
        return packageName
    }

    fun setPackageName(packageName: String) {
        this.packageName = packageName
    }

    fun getPageId(): Int {
        return pageId
    }

    fun setPageId(pageId: Int) {
        this.pageId = pageId
    }

    fun getTotalPackages(): Int {
        return totalPackages
    }

    fun setTotalPackages(totalPackages: Int) {
        this.totalPackages = totalPackages
    }

    fun getDestinationIP(): IP {
        return destinationIP
    }

    fun setDestinationIP(destinationIP: IP) {
        this.destinationIP = destinationIP
    }

    fun getActualIP(): IP {
        return actualIP
    }

    fun setActualIP(actualIP: IP) {
        this.actualIP = actualIP
    }

    fun getNextIP(): IP {
        return nextIP
    }

    fun setNextIP(originIP: IP) {
        this.nextIP = originIP
    }

}