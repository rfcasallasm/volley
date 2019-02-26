import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembershipCard } from 'app/shared/model/membership-card.model';

@Component({
    selector: 'jhi-membership-card-detail',
    templateUrl: './membership-card-detail.component.html'
})
export class MembershipCardDetailComponent implements OnInit {
    membershipCard: IMembershipCard;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ membershipCard }) => {
            this.membershipCard = membershipCard;
        });
    }

    previousState() {
        window.history.back();
    }
}
