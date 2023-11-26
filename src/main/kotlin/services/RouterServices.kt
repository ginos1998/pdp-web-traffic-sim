package services

import models.Router
import models.Package
import models.Page

class RouterServices {
    fun isPackageInDestiny(actualRouter: Router): Boolean {
        var inDestiny = false

        actualRouter.getOutputBuffer().forEach { (routerId, queue) ->
            if (queue.isNotEmpty() && actualRouter.getRouterId() == routerId) {
                inDestiny = true
                return@forEach
            }
        }

        return inDestiny
    }

    fun isReadyToBuildPage(actualRouter: Router): Boolean {
        var ready = false

        actualRouter.getOutputBuffer()[actualRouter.getRouterId()]
            ?.let { queue ->
                if (queue.isNotEmpty()) {
                    var packConNumeroMasAlto: Package = queue.peek()

                    queue.forEach { pack ->
                        if (pack.getNumber() > packConNumeroMasAlto.getNumber()) {
                            packConNumeroMasAlto = pack
                        }
                    }

                    ready = packConNumeroMasAlto.getNumber() == packConNumeroMasAlto.getTotalPackages()
                }
            }

        return ready
    }

    fun buildPage(actualRouter: Router): Page? {
        val somePackage = actualRouter.getOutputBuffer()[actualRouter.getRouterId()]?.peek()
        var pageName = ""
        actualRouter.getOutputBuffer()[actualRouter.getRouterId()]
            ?.forEach { pack ->
                pageName += pack.getPackageName()
            }

        return somePackage?.let {
            Page(
                it.getPageId(),
                pageName,
                somePackage.getTotalPackages(),
                somePackage.getDestinationIP().getRouterID(),
                somePackage.getOriginIP().getRouterID()
            )
        }


    }
}