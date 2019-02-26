import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from './postal-address.service';

@Component({
    selector: 'jhi-postal-address-update',
    templateUrl: './postal-address-update.component.html'
})
export class PostalAddressUpdateComponent implements OnInit {
    postalAddress: IPostalAddress;
    isSaving: boolean;

    constructor(protected postalAddressService: PostalAddressService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ postalAddress }) => {
            this.postalAddress = postalAddress;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.postalAddress.id !== undefined) {
            this.subscribeToSaveResponse(this.postalAddressService.update(this.postalAddress));
        } else {
            this.subscribeToSaveResponse(this.postalAddressService.create(this.postalAddress));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostalAddress>>) {
        result.subscribe((res: HttpResponse<IPostalAddress>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
