package models

class Package( // constructor
    private var packageId: Int,
    private var packageName: String,
    private var pageId: Int,
    private var number: Int,
    private var totalPackages: Int,
    private var destinationIP: IP,
    private var actualIP: IP,
    private var originIP: IP,
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

    fun getNumber(): Int {
        return number
    }

    fun setNumber(number: Int) {
        this.number = number
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

    fun getOriginIP(): IP {
        return originIP
    }

    fun setOriginIP(originIP: IP) {
        this.originIP = originIP
    }

}