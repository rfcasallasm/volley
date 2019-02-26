import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from './postal-address.service';

@Component({
    selector: 'jhi-postal-address-delete-dialog',
    templateUrl: './postal-address-delete-dialog.component.html'
})
export class PostalAddressDeleteDialogComponent {
    postalAddress: IPostalAddress;

    constructor(
        protected postalAddressService: PostalAddressService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.postalAddressService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'postalAddressListModification',
                content: 'Deleted an postalAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-postal-address-delete-popup',
    template: ''
})
export class PostalAddressDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ postalAddress }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PostalAddressDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.postalAddress = postalAddress;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/postal-address', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/postal-address', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
