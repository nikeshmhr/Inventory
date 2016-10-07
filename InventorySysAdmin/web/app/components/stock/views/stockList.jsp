<%-- 
    Document   : stockList
    Created on : Oct 1, 2016, 7:58:55 PM
    Author     : Nikesh Maharjan
--%>
<div class="container-fluid">
    <div class="col-md-4 col-sm-6 col-xs-12" ng-repeat="stock in stockCtrl.itemStocks">
        <div class="card-wrapper">
            <div class="card-header">
                <div class="col-md-6 col-sm-6 col-xs-12 header-content-left">
                    <div class="col-md-12 header-text">
                        Item
                    </div>
                    <div class="col-md-12 header-value">
                        {{stockCtrl.getItemById(stock.item).itemName || "N/A"}}
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-12 header-content-right">
                    <div class="col-md-12 header-text">
                        Stock
                    </div>
                    <div class="col-md-12 header-value">
                        {{stock.availableNoOfStocks}}
                    </div>
                </div>
                <span class="clearfix"></span>
            </div>
            <div class="card-body">
                <div class="col-md-6 col-sm-6 col-xs-12 body-content-left">
                    Purchase: Rs {{stockCtrl.getItemById(stock.item).purchasePrice}}
                </div>
                <div class="col-md-6 col-sm-6 col-xs-12 body-content-right">
                    Sales: Rs {{stockCtrl.getItemById(stock.item).salePrice}}
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="card-footer">
                <div class="col-md-12 col-sm-12 footer-summary">
                    Last Modified Date: {{stockCtrl.getItemById(stock.item).lastModifiedDate|date:'MMMM dd, yyyy'}}
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>