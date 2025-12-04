const Header = () => {

    return (
        <header className="">
            <nav className="border-b border-slate-800 bg-slate-900 backdrop-blur-md sticky top-0 z-50 text-slate-200">
                <div className="max-w-6xl mx-auto px-4 h-16 flex items-center justify-between">
                    <div className="flex items-center gap-2 text-blue-400">
                        {/*<ShieldCheck className="w-8 h-8" />*/}
                        <span className="font-bold text-xl tracking-tight text-white">SecHeader<span className="text-blue-500">Audit</span></span>
                    </div>
                    <div className="text-xs font-mono text-slate-500 hidden sm:block">
                        BLUE TEAM TOOLKIT v1.0
                    </div>
                </div>
            </nav>
        </header>
    )
}
export default Header;