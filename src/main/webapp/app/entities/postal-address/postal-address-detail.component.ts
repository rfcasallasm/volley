import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostalAddress } from 'app/shared/model/postal-address.model';

@Component({
    selector: 'jhi-postal-address-detail',
    templateUrl: './postal-address-detail.component.html'
})
export class PostalAddressDetailComponent implements OnInit {
    postalAddress: IPostalAddress;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ postalAddress }) => {
            this.postalAddress = postalAddress;
        });
    }

    previousState() {
        window.history.back();
    }
}
