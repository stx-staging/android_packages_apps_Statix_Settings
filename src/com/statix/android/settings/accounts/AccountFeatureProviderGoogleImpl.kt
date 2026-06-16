package com.google.android.settings.accounts

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.android.settings.accounts.AccountFeatureProvider
import com.android.settings.overlay.FeatureFactory
import com.statix.android.settings.R

class AccountFeatureProviderGoogleImpl : AccountFeatureProvider {
    override fun getAccountType(): String? {
        return FeatureFactory.appContext.getString(R.string.account_type)
    }

    override fun getAccounts(context: Context): Array<Account> {
        return AccountManager.get(context).getAccountsByType(getAccountType())
    }
}
